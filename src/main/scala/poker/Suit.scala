package poker

sealed abstract class Suit(val name: String, val rank: Int)
case object Clubs    extends Suit("C", 1)
case object Diamonds extends Suit("D", 2)
case object Hearts   extends Suit("H", 3)
case object Spades   extends Suit("S", 4)

object Suit {
  /**
   * While poker hands don't distinguish on suit,
   * we sometimes use an ordering on suits when selecting winning hands,
   * to avoid selecting different permutations of the same cards.
   */
  implicit val ordering: Ordering[Suit] =
    Ordering.by(_.rank)
}