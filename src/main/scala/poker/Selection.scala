package poker

/**
 * Data structure representing a "selection" of several cards from a hand:
 *
 * - the selected cards are stored in `selected`;
 * - the remaining cards are stored in `remaining`.
 *
 * Allows selection functions in `Hand` to "remember" which cards
 * are involved when building scoring hands.
 *
 * For example, we can build a full house by:
 *
 * - selecting three of a kind, and;
 * - selecting a pair from the remaining cards.
 */
case class Selection[A](selected: A, remaining: Hand)

object Selection {
  implicit def ordering[A](implicit ordering: Ordering[A]): Ordering[Selection[A]] =
    Ordering.by(_.selected)

  /**
   * Ordering that compares vectors of selections.
   * If the first entry in one vector beats the first entry in the other, it wins.
   * Else, if the second entry in one vector beats the second entry in the other, it wins.
   * Etc...
   */
  implicit def vectorOrdering[A](implicit ord: Ordering[Selection[A]]): Ordering[Vector[Selection[A]]] =
    new Ordering[Vector[Selection[A]]] {
      def compare(x: Vector[Selection[A]], y: Vector[Selection[A]]): Int =
        (x.headOption, y.headOption) match {
          case (None   , None   ) =>  0
          case (None   , Some(b)) => -1
          case (Some(a), None   ) => +1
          case (Some(a), Some(b)) =>
            val comp = ord.compare(a, b)
            if(comp == 0) compare(x.tail, y.tail) else comp
        }
    }
}
