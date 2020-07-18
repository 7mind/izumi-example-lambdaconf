package leaderboard

import distage.{DIResource, DIResourceBase}
import izumi.distage.model.definition.Activation
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.plugins.PluginConfig
import izumi.distage.roles
import izumi.distage.roles.RoleAppMain
import izumi.distage.roles.model.{RoleDescriptor, RoleService}
import izumi.functional.bio.BIOApplicative
import izumi.fundamentals.platform.cli.model.raw.{RawEntrypointParams, RawRoleParams}
import leaderboard.http.{HttpApi, HttpServer}
import logstage.LogBIO

import scala.annotation.unused

final class LeaderboardRole[F[+_, +_]: BIOApplicative](
  log: LogBIO[F],
  @unused api: HttpApi.Impl[F],
  @unused runningServer: HttpServer[F],
) extends RoleService[F[Throwable, *]] {
  override def start(roleParameters: RawEntrypointParams, freeArgs: Vector[String]): DIResourceBase[F[Throwable, *], Unit] = {
    DIResource.liftF(log.info("started!"))
  }
}

object LeaderboardRole extends RoleDescriptor {
  final val id = "leaderboard"
}

object LeaderBoardAppDummy
  extends MainBase(
    Activation(
      Repo -> Repo.Dummy
    )
  )

object LeaderBoardAppProd
  extends MainBase(
    Activation(
      Repo -> Repo.Prod
    )
  )

sealed abstract class MainBase(
  activation: Activation,
  override val requiredRoles: Vector[RawRoleParams] = Vector(RawRoleParams(LeaderboardRole.id))
) extends RoleAppMain.Default(
    launcher = new roles.RoleAppLauncher.LauncherBIO[zio.IO] {
      override protected def pluginConfig: PluginConfig = PluginConfig.cached(packagesEnabled = Seq("leaderboard.plugins"))

      override protected def requiredActivations: Activation = activation
    }
  )
