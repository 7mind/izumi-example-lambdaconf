package leaderboard.plugins

import cats.effect.Sync
import distage.config.ConfigModuleDef
import distage.{ModuleDef, TagKK}
import doobie.util.transactor.Transactor
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.plugins.PluginDef
import izumi.fundamentals.platform.integration.PortCheck
import leaderboard.LeaderboardRole
import leaderboard.config.{PostgresCfg, PostgresPortCfg}
import leaderboard.http.{HttpApi, HttpServer}
import leaderboard.repo._
import leaderboard.sql.{SQL, TransactorResource}
import org.http4s.dsl.Http4sDsl
import zio.IO
import zio.interop.catz._

import scala.concurrent.duration.DurationInt

object LeaderboardPlugins extends PluginDef {
  include(modules.repoDummy[IO])
  include(modules.repoProd[IO])
  include(modules.api[IO])
  include(modules.sql[IO])

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

    def sql[F[+_, +_]: TagKK]: ModuleDef = new ConfigModuleDef {
      make[SQL[F]].from[SQL.Impl[F]]
      make[Transactor[F[Throwable, *]]].fromResource[TransactorResource[F[Throwable, *]]]
      makeConfig[PostgresCfg]("postgres")
      makeConfig[PostgresPortCfg]("postgres")
      make[PortCheck].from(new PortCheck(3.seconds))
    }

    def repoDummy[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      tag(Repo.Dummy)

      make[Ladder[F]].fromResource[Ladder.DummyImpl[F]]
      make[Profiles[F]].fromResource[Profiles.DummyImpl[F]]
    }

    def repoProd[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      tag(Repo.Prod)

      make[Ladder[F]].fromResource[Ladder.PostgresImpl[F]]
      make[Profiles[F]].fromResource[Profiles.PostgresImpl[F]]
    }
  }
}
