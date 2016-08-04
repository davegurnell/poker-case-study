package poker

/**
 * An `Ordering` with the added notion of adjacency.
 */
trait Ranking[A] extends Ordering[A] {
  def apply(value: A): Int

  /**
   * Return:
   * - a negative `Int` if `x` is before `y` in the ordering;
   * - a positive `Int` if `x` is after `y` in the ordering;
   * - zero if `x` and `y` occupy the same place in the ordering.
   */
  def compare(x: A, y: A): Int =
    apply(y) - apply(x)

  /**
   * Is `x` an adjacent predecessor to `y` in the ranking?
   */
  def pred(x: A, y: A): Boolean =
    apply(x) + 1 == apply(y)
}

object Ranking {
  /**
   * Create a `Ranking[A]` from a mapping from `A` to `Int`.
   */
  def by[A](func: A => Int): Ranking[A] =
    new Ranking[A] {
      def apply(value: A): Int =
        func(value)
    }
}
