package leaderboard

import distage.ModuleDef
import izumi.distage.plugins.PluginConfig
import izumi.distage.testkit.TestConfig
import izumi.distage.testkit.scalatest.{AssertIO, DistageBIOEnvSpecScalatest}
import izumi.functional.bio.{BIO, F}
import leaderboard.model.{Score, UserId}
import leaderboard.repo.Ladder
import org.scalacheck.Gen.Parameters
import org.scalacheck._
import zio.{IO, ZIO}

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
}

abstract class LeaderboardTestEnv extends DistageBIOEnvSpecScalatest[ZIO] with AssertIO {
  override protected def config: TestConfig = TestConfig(
    pluginConfig = PluginConfig.cached(packagesEnabled = Seq("leaderboard.plugins")),
    moduleOverrides = new ModuleDef {
      make[Rnd[IO]].from[Rnd.Impl[IO]]
    },
    configBaseName = "leaderboard-test",
  )
}

abstract class LadderTest extends LeaderboardTestEnv {

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
  }
}

final class LadderTestDummy extends LadderTest
