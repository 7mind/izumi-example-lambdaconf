package leaderboard.repo

import leaderboard.model.{QueryFailure, Score, UserId}

trait Ladder[F[_, _]] {
  def submitScore(userId: UserId, score: Score): F[QueryFailure, Unit]
  def getScores: F[QueryFailure, List[(UserId, Score)]]
}

object Ladder {

  final class DummyImpl[F[+_, +_]] extends Ladder[F] {
    override def submitScore(userId: UserId, score: Score): F[QueryFailure, Unit] = ???

    override def getScores: F[QueryFailure, List[(UserId, Score)]] = ???
  }

  final class PostgresImpl[F[+_, +_]] extends Ladder[F] {
    override def submitScore(userId: UserId, score: Score): F[QueryFailure, Unit] = ???

    override def getScores: F[QueryFailure, List[(UserId, Score)]] = ???
  }

}
