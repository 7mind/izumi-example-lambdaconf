package leaderboard.repo

import izumi.distage.model.definition.DIResource
import izumi.functional.bio.{BIOApplicative, BIOPrimitives, F}
import leaderboard.model.{QueryFailure, Score, UserId}

trait Ladder[F[_, _]] {
  def submitScore(userId: UserId, score: Score): F[QueryFailure, Unit]
  def getScores: F[QueryFailure, List[(UserId, Score)]]
}

object Ladder {

  final class DummyImpl[F[+_, +_]: BIOApplicative: BIOPrimitives]
    extends DIResource.LiftF[F[Nothing, *], Ladder[F]](for {
      state <- F.mkRef(Map.empty[UserId, Score])
    } yield {
      new Ladder[F] {
        override def submitScore(userId: UserId, score: Score): F[QueryFailure, Unit] = {
          state.update_(_ + (userId -> score))
        }

        override def getScores: F[QueryFailure, List[(UserId, Score)]] = {
          state.get.map(_.toList.sortBy(_._2)(Ordering[Score].reverse))
        }
      }
    })

  final class PostgresImpl[F[+_, +_]] extends Ladder[F] {
    override def submitScore(userId: UserId, score: Score): F[QueryFailure, Unit] = ???

    override def getScores: F[QueryFailure, List[(UserId, Score)]] = ???
  }

}
