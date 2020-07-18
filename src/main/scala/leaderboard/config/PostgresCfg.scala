package leaderboard.config

final case class PostgresCfg(
  user: String,
  password: String,
  jdbcDriver: String,
  url: String,
)
