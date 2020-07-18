package leaderboard.plugins

import cats.effect.Sync
import distage.{ModuleDef, TagKK}
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.plugins.PluginDef
import leaderboard.LeaderboardRole
import leaderboard.http.{HttpApi, HttpServer}
import leaderboard.repo._
import org.http4s.dsl.Http4sDsl
import zio.IO
import zio.interop.catz._
import zio.interop.catz.implicits._

object LeaderboardPlugins extends PluginDef {
  include(modules.repoDummy[IO])
  include(modules.api[IO])

  object modules {
    def api[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      addImplicit[Sync[IO[Throwable, *]]]

      make[Ranks[F]].from[Ranks.Impl[F]]
      make[HttpApi.Impl[F]]
      many[HttpApi[F]]
        .weak[HttpApi.Impl[F]]
      make[HttpServer[F]].fromResource[HttpServer.HttpServerImpl[F]]
      make[Http4sDsl[F[Throwable, *]]]
      make[LeaderboardRole[F]]
    }

    def repoDummy[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      tag(Repo.Dummy)

      make[Ladder[F]].fromResource[Ladder.DummyImpl[F]]
      make[Profiles[F]].fromResource[Profiles.DummyImpl[F]]
    }
  }
}
