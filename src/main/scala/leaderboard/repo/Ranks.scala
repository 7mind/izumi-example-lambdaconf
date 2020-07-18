package leaderboard.repo

import leaderboard.model.{QueryFailure, RankedProfile, UserId}
import zio.{Has, IO, ZIO, ZLayer}

trait Ranks[F[_, _]] {
  def getRank(userId: UserId): F[QueryFailure, Option[RankedProfile]]
}

object Ranks {
  def zlayer: ZLayer[Has[Profiles[IO]] with Has[Ladder[IO]], Nothing, Has[Ranks[IO]]] =
    ZLayer.fromEffect(for {
      ladder   <- ZIO.service[Ladder[IO]]
      profiles <- ZIO.service[Profiles[IO]]
    } yield new Ranks[IO] {

      override def getRank(userId: UserId): IO[QueryFailure, Option[RankedProfile]] = {
        for {
          maybeProfile <- profiles.getProfile(userId)
          scores       <- ladder.getScores
          res = for {
            profile <- maybeProfile
            rank     = scores.indexWhere(_._1 == userId)
            score   <- scores.find(_._1 == userId).map(_._2)
          } yield {
            RankedProfile(
              name        = profile.name,
              description = profile.description,
              rank        = rank,
              score       = score,
            )
          }
        } yield {
          res
        }
      }
    })
}
