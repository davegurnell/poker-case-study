package poker

case class Hand(cards: Vector[Card])

object Hand {
  def apply(cards: Card*): Hand =
    Hand(cards.toVector)
}
