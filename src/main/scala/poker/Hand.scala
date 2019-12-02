package poker

case class Hand(cards: List[Card])

object Hand {
  def apply(cards: Card*): Hand =
    Hand(cards.toList)
}
