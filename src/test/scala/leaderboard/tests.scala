package leaderboard

import distage.{Activation, DIKey, ModuleDef}
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.plugins.PluginConfig
import izumi.distage.testkit.TestConfig
import izumi.distage.testkit.scalatest.{AssertIO, DistageBIOEnvSpecScalatest}
import izumi.functional.bio.{BIO, F}
import leaderboard.model.{QueryFailure, RankedProfile, Score, UserId, UserProfile}
import leaderboard.repo.{Ladder, Profiles, Ranks}
import org.scalacheck.Gen.Parameters
import Rnd.rnd
import leaderboard.axis.Scene
import org.scalacheck._
import zio.{Has, IO, ZIO}

trait Rnd[F[_, _]] {
  def apply[A: Arbitrary]: F[Nothing, A]
}

object Rnd {
  final class Impl[F[+_, +_]: BIO] extends Rnd[F] {
    override def apply[A: Arbitrary]: F[Nothing, A] = {
      F.sync {
        val (p, s) = Prop.startSeed(Parameters.default)
        Arbitrary.arbitrary[A].pureApply(p, s)
      }
    }
  }

  object rnd extends Rnd[ZIO[Has[Rnd[IO]], *, *]] {
    override def apply[A: Arbitrary]: ZIO[Has[Rnd[IO]], Nothing, A] = ZIO.accessM(_.get.apply[A])
  }
}

object ladder extends Ladder[ZIO[Has[Ladder[IO]], ?, ?]] {
  def submitScore(userId: UserId, score: Score): ZIO[Has[Ladder[IO]], QueryFailure, Unit] = ZIO.accessM(_.get.submitScore(userId, score))
  def getScores: ZIO[Has[Ladder[IO]], QueryFailure, List[(UserId, Score)]]                = ZIO.accessM(_.get.getScores)
}

object profiles extends Profiles[ZIO[Has[Profiles[IO]], ?, ?]] {
  override def setProfile(userId: UserId, profile: UserProfile): ZIO[Has[Profiles[IO]], QueryFailure, Unit] = ZIO.accessM(_.get.setProfile(userId, profile))
  override def getProfile(userId: UserId): ZIO[Has[Profiles[IO]], QueryFailure, Option[UserProfile]]        = ZIO.accessM(_.get.getProfile(userId))
}

object ranks extends Ranks[ZIO[Has[Ranks[IO]], ?, ?]] {
  override def getRank(userId: UserId): ZIO[Has[Ranks[IO]], QueryFailure, Option[RankedProfile]] = ZIO.accessM(_.get.getRank(userId))
}

abstract class LeaderboardTestEnv extends DistageBIOEnvSpecScalatest[ZIO] with AssertIO {
  override protected def config: TestConfig = TestConfig(
    pluginConfig = PluginConfig.cached(packagesEnabled = Seq("leaderboard.plugins")),
    moduleOverrides = new ModuleDef {
      make[Rnd[IO]].from[Rnd.Impl[IO]]
    },
    configBaseName = "leaderboard-test",
    memoizationRoots = Set(
      DIKey[Ladder[IO]],
      DIKey[Profiles[IO]],
    ),
  )
}

trait WithDummy extends DistageBIOEnvSpecScalatest[ZIO] {
  override protected def config: TestConfig = super.config.copy(
    activation = Activation(Repo -> Repo.Dummy)
  )
}

trait WithProd extends DistageBIOEnvSpecScalatest[ZIO] {
  override protected def config: TestConfig = super.config.copy(
    activation = Activation(
      Repo  -> Repo.Prod,
      Scene -> Scene.Managed,
    )
  )
}

final class LadderTestDummy extends LadderTest with WithDummy
final class LadderTestProd extends LadderTest with WithProd

final class ProfilesTestDummy extends ProfilesTest with WithDummy
final class ProfilesTestProd extends ProfilesTest with WithProd

final class RanksTestDummy extends RanksTest with WithDummy
final class RanksTestProd extends RanksTest with WithProd

sealed abstract class LadderTest extends LeaderboardTestEnv {

  "Ladder" should {

    /**
      * this test gets dependencies injected through function arguments
      */
    "submit & get" in {
      (rnd: Rnd[IO], ladder: Ladder[IO]) =>
        for {
          user  <- rnd[UserId]
          score <- rnd[Score]
          _     <- ladder.submitScore(user, score)
          res   <- ladder.getScores.map(_.find(_._1 == user).map(_._2))
          _     <- assertIO(res contains score)
        } yield ()
    }

    /**
      * this test get dependencies injected via ZIO Env:
      */
    "assign a higher position in the list to a higher score" in {
      for {
        user1  <- rnd[UserId]
        score1 <- rnd[Score]
        user2  <- rnd[UserId]
        score2 <- rnd[Score]

        _      <- ladder.submitScore(user1, score1)
        _      <- ladder.submitScore(user2, score2)
        scores <- ladder.getScores

        user1Rank = scores.indexWhere(_._1 == user1)
        user2Rank = scores.indexWhere(_._1 == user2)

        _ <-
          if (score1 > score2) {
            assertIO(user1Rank < user2Rank)
          } else if (score2 > score1) {
            assertIO(user2Rank < user1Rank)
          } else IO.unit
      } yield ()
    }
  }
}

abstract class ProfilesTest extends LeaderboardTestEnv {

  "Profiles" should {

    /**
      * that's what the ZIO signature looks like for ZIO Env injection:
      */
    "set & get" in {
      val zioValue: ZIO[Has[Profiles[IO]] with Has[Rnd[IO]], QueryFailure, Unit] = for {
        user   <- rnd[UserId]
        name   <- rnd[String]
        desc   <- rnd[String]
        profile = UserProfile(name, desc)
        _      <- profiles.setProfile(user, profile)
        res    <- profiles.getProfile(user)
        _      <- assertIO(res contains profile)
      } yield ()
      zioValue
    }

  }

}

abstract class RanksTest extends LeaderboardTestEnv {

  "Ranks" should {

    /**
      * you can use Argument injection and ZIO Env injection at the same time:
      */
    "return None for a user with no score" in {
      (ranks: Ranks[IO]) =>
        for {
          user   <- rnd[UserId]
          name   <- rnd[String]
          desc   <- rnd[String]
          profile = UserProfile(name, desc)
          _      <- profiles.setProfile(user, profile)
          res1   <- ranks.getRank(user)
          _      <- assertIO(res1.isEmpty)
        } yield ()
    }

    "return None for a user with no profile" in {
      for {
        user  <- rnd[UserId]
        score <- rnd[Score]
        _     <- ladder.submitScore(user, score)
        res1  <- ranks.getRank(user)
        _     <- assertIO(res1.isEmpty)
      } yield ()
    }

    "assign a higher rank to a user with more score" in {
      for {
        user1  <- rnd[UserId]
        name1  <- rnd[String]
        desc1  <- rnd[String]
        score1 <- rnd[Score]

        user2  <- rnd[UserId]
        name2  <- rnd[String]
        desc2  <- rnd[String]
        score2 <- rnd[Score]

        _ <- profiles.setProfile(user1, UserProfile(name1, desc1))
        _ <- ladder.submitScore(user1, score1)

        _ <- profiles.setProfile(user2, UserProfile(name2, desc2))
        _ <- ladder.submitScore(user2, score2)

        user1Rank <- ranks.getRank(user1).map(_.get.rank)
        user2Rank <- ranks.getRank(user2).map(_.get.rank)

        _ <-
          if (score1 > score2) {
            assertIO(user1Rank < user2Rank)
          } else if (score2 > score1) {
            assertIO(user2Rank < user1Rank)
          } else IO.unit
      } yield ()
    }

  }

}
