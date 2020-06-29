package poker

sealed abstract class Suit(val name: String)

case object Clubs    extends Suit("C")
case object Diamonds extends Suit("D")
case object Hearts   extends Suit("H")
case object Spades   extends Suit("S")

object Suit {

  /**
    * While poker hands don't distinguish on suit,
    * we sometimes use an ordering on suits when selecting winning hands,
    * to avoid selecting different permutations of the same cards.
    */
  implicit val ordering: Ordering[Suit] =
    Ordering.by {
      case Clubs    => 1
      case Diamonds => 2
      case Hearts   => 3
      case Spades   => 4
    }
}
