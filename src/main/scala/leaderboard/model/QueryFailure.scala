package leaderboard.model

sealed trait AppFailure extends RuntimeException

final case class QueryFailure(queryName: String, cause: Throwable)
  extends RuntimeException(s"""Query "$queryName" failed with ${cause.getMessage}""", cause)
  with AppFailure
