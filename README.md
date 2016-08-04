# Poker Hand Comparison Case Study

Scala case stufy about identifying and comparing scoring poker hands.
Based on a Kata in the [Coding Dojo Handbook](https://leanpub.com/codingdojohandbook)
by Emily Bache.

Licensed [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Getting Started

To run the unit tests:

~~~bash
$ ./sbt.sh test
~~~

## Synopsis

There are two crucial sets of functionality:

- "selection methods" on `Hand`, which select scoring card combinations;
- "orderings" that compare `Hands` in equivalent scoring categories.

The selection methods all return objects of type `Selection`,
identifying a set of scoring cards
and retaining information about other cards not used in the selection.

For example, `choosePair` selects all single pair combinations:

```scala
import poker._, poker.syntax._
// import poker._
// import poker.syntax._

val hand = h"2C 2D 3S 3D 3H"
// hand: Hand = Hand(2C,2D,3S,3D,3H)

val pairs = hand.choosePair
// pairs: List[Selection[(Card, Card)]] =
//   List(
//     Selection((2C,2D),Hand(3S,3D,3H)),
//     Selection((3D,3S),Hand(2C,2D,3H)),
//     Selection((3D,3H),Hand(2C,2D,3S)),
//     Selection((3H,3S),Hand(2C,2D,3D)))
```

`List` is a monad, so we can use `flatMap` to
generate combinations of selections.
This is useful when selecting the more complex scoring hands:

```scala
val fullHouse = for {
  Selection(triple, rest) <- hand.threeOfAKind
  Selection(pair,   rest) <- rest.choosePair
} yield Selection((triple, pair), rest)
// fullHouse: List[Selection[((Card, Card, Card), (Card, Card))]] =
//   List(
//     Selection(((3D,3H,3S),(2C,2D)),Hand()))
```

The orderings do the rest of the work.
By chaining implicits together, we can compare
cards, hands, and selections.
Note that we're using Java-style semantics here:
`compare(x, y)` returns a negative `Int` if `x < y`,
positive if `x > y`, and zero if `x == y`:

```scala
// Helper function:
def compare[A](x: A, y: A)(implicit o: Ordering[A]): Int =
  o.compare(x, y)
// compare: [A](x: A, y: A)(implicit o: Ordering[A])Int

// Compare cards using a custom Ordering:
compare(c"2C", c"5D")
// res0: Int = -3

compare(c"2C", c"2D")
// res1: Int = 0

// Compare pairs of cards using the built-in Orderings for tuples:
compare((c"2C", c"2D"), (c"5D", c"5H"))
// res2: Int = -3

// The Ordering for Selection compares based on the selected cards:
compare(Selection((c"5D", c"5H"), Hand()), Selection((c"2H", c"2D"), Hand()))
// res3: Int = 1
```

We can even compare values of type `List[Selection]`
using a custom `Ordering` in `Selection.scala`,
meaning we can compare hands using a particular scoring category:

```scala
Hand.singlePair
// res4: Ordering[poker.Hand]

Hand.singlePair.compare(h"2C 2D 3S 3D 4H", h"2C 2D 2S 3D 3H")
// res5: Int = 1
```

In this example, the first hand beats the second
because we're only comparing based on the highest pair.
Note that the second hand is actually better because it's a full house.

To get a complete ordering on poker hands,
we compare first on the highest scoring category,
then on the next, and so on:

- straight flush
- four of a kind
- full house
- flush
- straight
- three of a kind
- two pairs
- single pair
- highest card

We do this using a handy custom `orElse` method
that allows us to chain orderings:

```scala
val handOrdering: Ordering[Hand] = {
  Hand.straightFlush orElse
  Hand.fourOfAKind   orElse
  Hand.fullHouse     orElse
  Hand.flush         orElse
  Hand.straight      orElse
  Hand.threeOfAKind  orElse
  Hand.twoPairs      orElse
  Hand.singlePair    orElse
  Hand.highestCard
}
```

This `Ordering`, which is the same as
the default ordering defined in `Hand.ordering`,
will compare any two hands successfully:

```scala
handOrdering.compare(h"2C 2D 3S 3D 4H", h"2C 2D 2S 3D 3H")
// res6: Int = -1
```

There is a `hand.beats` method to make this clearer:

```scala
h"2C 2D 3S 3D 4H" beats h"2C 2D 2S 3D 3H"
// res7: Boolean = false

h"2C 2D 2S 3D 3H" beats h"2C 2D 3S 3D 4H"
// res8: Boolean = true
```
