package leaderboard.repo

import leaderboard.model.{QueryFailure, UserId, UserProfile}

trait Profiles[F[_, _]] {
  def setProfile(userId: UserId, profile: UserProfile): F[QueryFailure, Unit]
  def getProfile(userId: UserId): F[QueryFailure, Option[UserProfile]]
}

object Profiles {

  final class DummyImpl[F[+_, +_]] extends Profiles[F] {
    override def setProfile(userId: UserId, profile: UserProfile): F[QueryFailure, Unit] = ???

    override def getProfile(userId: UserId): F[QueryFailure, Option[UserProfile]] = ???
  }

  final class PostgresImpl[F[+_, +_]] extends Profiles[F] {
    override def setProfile(userId: UserId, profile: UserProfile): F[QueryFailure, Unit] = ???

    override def getProfile(userId: UserId): F[QueryFailure, Option[UserProfile]] = ???
  }

}
