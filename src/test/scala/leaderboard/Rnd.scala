package leaderboard

import org.scalacheck.Arbitrary

trait Rnd[F[_, _]] {
  def apply[A: Arbitrary]: F[Nothing, A]
}

object Rnd {
  final class Impl[F[+_, +_]] extends Rnd[F] {
    override def apply[A: Arbitrary]: F[Nothing, A] = ???
  }
}
