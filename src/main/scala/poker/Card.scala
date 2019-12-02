package poker

case class Card(value: Value, suit: Suit) {
  override def toString: String =
    s"${value.name}${suit.name}"
}
