package leaderboard.plugins

import java.util.concurrent.ThreadPoolExecutor

import cats.effect.{Async, Blocker, Bracket, ConcurrentEffect, ContextShift, Timer}
import distage.plugins.PluginDef
import distage.{Id, ModuleDef, TagKK}
import izumi.distage.effect.modules.ZIODIEffectModule
import izumi.distage.model.definition.StandardAxis.Repo
import izumi.distage.plugins.PluginDef
import leaderboard.repo._
import logstage.LogBIO
import zio.{IO, Task}
import zio.interop.catz.taskEffectInstance

import scala.concurrent.ExecutionContext

object LeaderboardPlugins extends PluginDef {
  include(modules.repoDummy[IO])

  object modules {
    def api[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      make[Ranks[F]].from[Ranks.Impl[F]]
    }

    def repoDummy[F[+_, +_]: TagKK]: ModuleDef = new ModuleDef {
      tag(Repo.Dummy)

      make[Ladder[F]].fromResource[Ladder.DummyImpl[F]]
      make[Profiles[F]].fromResource[Profiles.DummyImpl[F]]
    }
  }
}

import zio.interop.catz._
import zio.interop.catz.implicits._

object ZIOPlugin extends PluginDef {
  include(ZIODIEffectModule)

  addImplicit[Bracket[Task, Throwable]]
  addImplicit[Async[Task]]
  addImplicit[ContextShift[Task]]
  addImplicit[Timer[Task]]
  make[ConcurrentEffect[Task]].from {
    runtime: zio.Runtime[Any] =>
      taskEffectInstance(runtime)
  }

  make[Blocker].from {
    pool: ThreadPoolExecutor @Id("zio.io") =>
      Blocker.liftExecutionContext(ExecutionContext.fromExecutorService(pool))
  }

  make[LogBIO[IO]].from(LogBIO.fromLogger[IO] _)
}
