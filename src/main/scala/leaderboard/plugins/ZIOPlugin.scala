package leaderboard.plugins

import java.util.concurrent.ThreadPoolExecutor

import cats.effect.{Async, Blocker, Bracket, ConcurrentEffect, ContextShift, Timer}
import distage.Id
import izumi.distage.effect.modules.{CatsDIEffectModule, ZIODIEffectModule}
import izumi.distage.plugins.PluginDef
import logstage.LogBIO
import zio.interop.catz.taskEffectInstance
import zio.{IO, Task}

import scala.concurrent.ExecutionContext
import zio.interop.catz._
import zio.interop.catz.implicits._

object ZIOPlugin extends PluginDef {
  include(ZIODIEffectModule)
  include(CatsDIEffectModule)

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
