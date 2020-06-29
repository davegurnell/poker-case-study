package poker

case class Card(value: Value, suit: Suit) {
  override def toString: String =
    s"${value.name}${suit.name}"
}

object Card {
  val acesHigh: Ordering[Card] =
    ordering(Value.acesHigh)

  val acesLow: Ordering[Card] =
    ordering(Value.acesLow)

  private def ordering(implicit valueOrdering: Ordering[Value]): Ordering[Card] =
    Ordering.by(card => (card.value, card.suit))
}
