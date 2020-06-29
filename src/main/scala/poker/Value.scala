package poker

sealed abstract class Value(val name: String) extends Product with Serializable {
  def acesLowRanking: Int = this match {
    case Ace   => 1
    case Two   => 2
    case Three => 3
    case Four  => 4
    case Five  => 5
    case Six   => 6
    case Seven => 7
    case Eight => 8
    case Nine  => 9
    case Ten   => 10
    case Jack  => 11
    case Queen => 12
    case King  => 13
  }

  def acesHighRanking: Int = this match {
    case Two   => 2
    case Three => 3
    case Four  => 4
    case Five  => 5
    case Six   => 6
    case Seven => 7
    case Eight => 8
    case Nine  => 9
    case Ten   => 10
    case Jack  => 11
    case Queen => 12
    case King  => 13
    case Ace   => 14
  }
}

case object Ace   extends Value("A")
case object Two   extends Value("2")
case object Three extends Value("3")
case object Four  extends Value("4")
case object Five  extends Value("5")
case object Six   extends Value("6")
case object Seven extends Value("7")
case object Eight extends Value("8")
case object Nine  extends Value("9")
case object Ten   extends Value("T")
case object Jack  extends Value("J")
case object Queen extends Value("Q")
case object King  extends Value("K")

object Value {
  val acesLow: Ordering[Value] =
    Ordering.by(_.acesLowRanking)

  val acesHigh: Ordering[Value] =
    Ordering.by(_.acesHighRanking)
}
