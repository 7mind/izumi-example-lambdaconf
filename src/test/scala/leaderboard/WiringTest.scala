package leaderboard

import com.typesafe.config.ConfigFactory
import distage.{DIKey, Injector}
import izumi.distage.config.AppConfigModule
import izumi.distage.model.definition.Activation
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.model.plan.Roots
import izumi.distage.testkit.scalatest.DistageBIOEnvSpecScalatest
import izumi.logstage.api.logger.LogRouter
import leaderboard.axis.Scene
import leaderboard.plugins.{DockerPlugin, LeaderboardPlugin, ZIOPlugin}
import logstage.di.LogstageModule
import zio.{IO, Task, ZIO}

final class WiringTest extends DistageBIOEnvSpecScalatest[ZIO] {

  "test wiring" in {
    def checkActivation(activation: Activation): Task[Unit] = {
      Task {
        val plan = Injector(activation)
          .plan(
            Seq(
              LeaderboardPlugin,
              ZIOPlugin,
              DockerPlugin,
              // dummy logger + config modules,
              // normally the RoleAppMain or the testkit will provide real values here
              new LogstageModule(LogRouter.nullRouter, setupStaticLogRouter = false),
              new AppConfigModule(ConfigFactory.empty),
            ).merge,
            roots = Roots(
              DIKey[LeaderboardRole[IO]],
              DIKey[LadderRole[IO]],
              DIKey[ProfileRole[IO]],
            ),
          )
          .assertImportsResolvedOrThrow()
      }
    }

    for {
      _ <- checkActivation(Activation(Repo -> Repo.Dummy))
      _ <- checkActivation(Activation(Repo -> Repo.Prod, Scene -> Scene.Provided))
      _ <- checkActivation(Activation(Repo -> Repo.Prod, Scene -> Scene.Managed))
    } yield ()
  }

}
