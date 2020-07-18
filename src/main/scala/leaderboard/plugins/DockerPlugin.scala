package leaderboard.plugins

import distage.ModuleDef
import izumi.distage.docker.Docker.{AvailablePort, ClientConfig}
import izumi.distage.docker.examples.{PostgresDocker, PostgresDockerModule}
import izumi.distage.docker.modules.DockerSupportModule
import izumi.distage.plugins.PluginDef
import leaderboard.axis.Scene
import leaderboard.config.PostgresPortCfg
import zio.Task

object DockerPlugin extends PluginDef {
  include(DockerSupportModule[Task])
  include(PostgresDockerModule[Task])

  make[PostgresPortCfg]
    .tagged(Scene.Managed)
    .from {
      postgresContainer: PostgresDocker.Container =>
        postgresContainer.availablePorts.first(PostgresDocker.primaryPort) match {
          case AvailablePort(hostV4, port) =>
            PostgresPortCfg(hostV4, port)
        }
    }
}
