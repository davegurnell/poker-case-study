package poker

case class Card(value: Value, suit: Suit) {
  override def toString: String =
    s"${value.name}${suit.name}"
}

object Card {
  val acesHigh: Ranking[Card] =
    Ranking.by(card => Value.acesHigh(card.value))

  val acesLow: Ranking[Card] =
    Ranking.by(card => Value.acesLow(card.value))

  implicit val ordering: Ordering[Card] =
    acesHigh.reverse
}
