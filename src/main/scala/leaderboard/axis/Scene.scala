package leaderboard.axis

import izumi.distage.model.definition.Axis

object Scene extends Axis {
  case object Provided extends AxisValueDef
  case object Managed extends AxisValueDef
}
