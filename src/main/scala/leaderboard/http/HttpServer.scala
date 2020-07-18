package leaderboard.http

import cats.effect.{ConcurrentEffect, Timer}
import cats.implicits._
import distage.Id
import izumi.distage.model.definition.DIResource
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._

import scala.concurrent.ExecutionContext

final case class HttpServer[F[_, _]](
  server: Server[F[Throwable, *]]
)

object HttpServer {
  final class HttpServerImpl[F[+_, +_]](
    apis: Set[HttpApi[F]],
    cpuPool: ExecutionContext @Id("zio.cpu")
  )(implicit
    concurrentEffect: ConcurrentEffect[F[Throwable, *]],
    timer: Timer[F[Throwable, *]]
  ) extends DIResource.Of[F[Throwable, *], HttpServer[F]](
      DIResource.fromCats {
        val combined = apis.map(_.http).toList.foldK

        BlazeServerBuilder[F[Throwable, ?]](cpuPool)
          .withHttpApp(combined.orNotFound)
          .bindLocal(8080)
          .resource
          .map(HttpServer(_))
      }
    )
}
