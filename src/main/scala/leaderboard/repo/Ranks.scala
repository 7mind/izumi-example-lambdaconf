package leaderboard.repo

import leaderboard.model.{QueryFailure, RankedProfile, UserId}

trait Ranks[F[_, _]] {
  def getRank(userId: UserId): F[QueryFailure, Option[RankedProfile]]
}

object Ranks {
  final class Impl[F[+_, +_]](
    ladder: Ladder[F],
    profiles: Profiles[F],
  ) extends Ranks[F] {

    override def getRank(userId: UserId): F[QueryFailure, Option[RankedProfile]] = ???

  }
}
