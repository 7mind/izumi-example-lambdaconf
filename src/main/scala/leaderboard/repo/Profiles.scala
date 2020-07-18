package leaderboard.repo

import izumi.distage.model.definition.DIResource
import leaderboard.model.{QueryFailure, UserId, UserProfile}
import izumi.functional.bio.{BIOApplicative, BIOPrimitives, F}

trait Profiles[F[_, _]] {
  def setProfile(userId: UserId, profile: UserProfile): F[QueryFailure, Unit]
  def getProfile(userId: UserId): F[QueryFailure, Option[UserProfile]]
}

object Profiles {

  final class DummyImpl[F[+_, +_]: BIOApplicative: BIOPrimitives]
    extends DIResource.LiftF[F[Nothing, *], Profiles[F]](for {
      state <- F.mkRef(Map.empty[UserId, UserProfile])
    } yield {
      new Profiles[F] {
        override def setProfile(userId: UserId, profile: UserProfile): F[QueryFailure, Unit] = {
          state.update_(_ + (userId -> profile))
        }

        override def getProfile(userId: UserId): F[QueryFailure, Option[UserProfile]] = {
          state.get.map(_.get(userId))
        }
      }
    })

  final class PostgresImpl[F[+_, +_]] extends Profiles[F] {
    override def setProfile(userId: UserId, profile: UserProfile): F[QueryFailure, Unit] = ???

    override def getProfile(userId: UserId): F[QueryFailure, Option[UserProfile]] = ???
  }

}
