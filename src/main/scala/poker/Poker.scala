package poker

object Poker {
  def compare(hand1: Hand, hand2: Hand): Int =
    Hand.ordering.compare(hand1, hand2)
}
