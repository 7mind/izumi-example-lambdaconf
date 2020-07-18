package leaderboard.plugins

import cats.effect.Sync
import distage.config.ConfigModuleDef
import distage.{ModuleDef, TagKK}
import doobie.util.transactor.Transactor
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.plugins.PluginDef
import izumi.distage.roles.bundled.BundledRolesModule
import izumi.fundamentals.platform.integration.PortCheck
import leaderboard.{LadderRole, LeaderboardRole, ProfileRole}
import leaderboard.axis.Scene
import leaderboard.config.{PostgresCfg, PostgresPortCfg}
import leaderboard.http.{HttpApi, HttpServer}
import leaderboard.repo._
import leaderboard.sql.{SQL, TransactorResource}
import org.http4s.dsl.Http4sDsl
import zio.IO
import zio.interop.catz._

import scala.concurrent.duration.DurationInt

object LeaderboardPlugin extends PluginDef {
  include(modules.repoDummy[IO])
  include(modules.repoProd[IO])
  include(modules.roles[IO])
  include(modules.api[IO])
  include(modules.sql[IO])

  object modules {
    def roles[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      make[LeaderboardRole[F]]
      make[LadderRole[F]]
      make[ProfileRole[F]]
      include(BundledRolesModule[F[Throwable, *]]("1.0.0"))
    }

    def api[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {

      make[Ranks[F]].from[Ranks.Impl[F]]

      make[HttpApi.LadderApi[F]]
      make[HttpApi.ProfileApi[F]]

      many[HttpApi[F]]
        .weak[HttpApi.LadderApi[F]]
        .weak[HttpApi.ProfileApi[F]]

      make[HttpServer[F]].fromResource[HttpServer.HttpServerImpl[F]]
      make[Http4sDsl[F[Throwable, *]]]
    }

    def sql[F[+_, +_]: TagKK]: ModuleDef = new ConfigModuleDef {
      make[SQL[F]].from[SQL.Impl[F]]
      make[Transactor[F[Throwable, *]]].fromResource[TransactorResource[F[Throwable, *]]]
      makeConfig[PostgresCfg]("postgres")
      makeConfig[PostgresPortCfg]("postgres")
        .tagged(Scene.Provided)
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
