package leaderboard.http

import cats.effect.Sync
import leaderboard.repo.{Ladder, Profiles, Ranks}
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

trait HttpApi[F[_, _]] {
  def http: HttpRoutes[F[Throwable, ?]]
}

object HttpApi {

  final class Impl[F[+_, +_]](
    dsl: Http4sDsl[F[Throwable, *]],
    ladder: Ladder[F],
    profiles: Profiles[F],
    ranks: Ranks[F],
  )(implicit sync: Sync[F[Throwable, *]],
  ) extends HttpApi[F] {

    import dsl._

    override def http: HttpRoutes[F[Throwable, *]] = {
      HttpRoutes.of[F[Throwable, *]] {
        case GET -> Root / "ladder" =>
          InternalServerError()

        case POST -> Root / "ladder" / UUIDVar(userId) / LongVar(score) =>
          InternalServerError()

        case GET -> Root / "profile" / UUIDVar(userId) =>
          InternalServerError()

        case rq @ POST -> Root / "profile" / UUIDVar(userId) =>
          InternalServerError()
      }
    }
  }

}
