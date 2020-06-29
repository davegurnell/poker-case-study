package poker

case class Hand(cards: Vector[Card]) {

  /**
    * Remove a card from this hand.
    */
  def -(card: Card): Hand =
    copy(cards = cards.filterNot(_ == card))

  /**
    * Add a card to this hand.
    */
  def +(card: Card): Hand =
    copy(cards = cards :+ card)

  /** Does this hand beat that hand? */
  def beats(that: Hand): Boolean =
    Hand.ordering.compare(this, that) > 0

  /**
    * All "card high" selections we can make from this hand,
    * ordered lowest to highest.
    */
  def chooseCard: Vector[Selection[Card]] =
    cards
      .map(card => Selection(card, this - card))
      .sorted(Selection.ordering(Card.ordering.reverse))

  /**
    * All "single pair" selections we can make from this hand,
    * ordered lowest to highest.
    */
  def choosePair: Vector[Selection[(Card, Card)]] = {
    import Suit.ordering._ // import the `<` operator for Suit
    for {
      Selection(a, rest) <- this.chooseCard
      Selection(b, rest) <- rest.chooseCard
      if a.value == b.value && a.suit < b.suit
    } yield Selection((a, b), rest)
  }

  /**
    * All "two pairs" selections we can make from this hand.
    */
  def twoPairs: Vector[Selection[((Card, Card), (Card, Card))]] =
    for {
      Selection(a, rest) <- this.choosePair
      Selection(b, rest) <- rest.choosePair
    } yield Selection((a, b), rest)

  /**
    * All "three of a kind" selections we can make from this hand.
    */
  def threeOfAKind: Vector[Selection[(Card, Card, Card)]] = {
    import Suit.ordering._
    for {
      Selection((a, b), rest) <- this.choosePair
      Selection(c, rest)      <- rest.chooseCard
      if b.value == c.value && b.suit < c.suit
    } yield Selection((a, b, c), rest)
  }

  /**
    * All "straight" selections we can make from this hand.
    */
  def straight(
      ranking: Ranking[Card]
  ): Vector[Selection[(Card, Card, Card, Card, Card)]] =
    for {
      Selection(a, rest) <- this.chooseCard
      Selection(b, rest) <- rest.chooseCard if ranking.pred(b, a)
      Selection(c, rest) <- rest.chooseCard if ranking.pred(c, b)
      Selection(d, rest) <- rest.chooseCard if ranking.pred(d, c)
      Selection(e, rest) <- rest.chooseCard if ranking.pred(e, d)
    } yield Selection((a, b, c, d, e), rest)

  /**
    * All "flush" selections we can make from this hand.
    */
  def flush: Vector[Selection[(Card, Card, Card, Card, Card)]] = {
    import Card.acesHigh._
    for {
      Selection(a, rest) <- this.chooseCard
      Selection(b, rest) <- rest.chooseCard if a.suit == b.suit && b < a
      Selection(c, rest) <- rest.chooseCard if a.suit == c.suit && c < b
      Selection(d, rest) <- rest.chooseCard if a.suit == d.suit && d < c
      Selection(e, rest) <- rest.chooseCard if a.suit == e.suit && e < d
    } yield Selection((a, b, c, d, e), rest)
  }

  /**
    * All "full house" selections we can make from this hand.
    */
  def fullHouse: Vector[Selection[((Card, Card, Card), (Card, Card))]] =
    for {
      Selection(three, rest) <- this.threeOfAKind
      Selection(pair, rest)  <- rest.choosePair
    } yield Selection((three, pair), rest)

  /**
    * All "four of a kind" selections we can make from this hand.
    */
  def fourOfAKind: Vector[Selection[(Card, Card, Card, Card)]] = {
    import Suit.ordering._
    for {
      Selection((a, b, c), rest) <- this.threeOfAKind
      Selection(d, rest)         <- rest.chooseCard
      if c.value == d.value && c.suit < d.suit
    } yield Selection((a, b, c, d), rest)
  }

  /**
    * Return all "straight flush" selections we can make from this hand.
    */
  def straightFlush(ranking: Ranking[Card]): Vector[Selection[(Card, Card, Card, Card, Card)]] =
    for {
      Selection(a, rest) <- this.chooseCard
      Selection(b, rest) <- rest.chooseCard if a.suit == b.suit && ranking.pred(b, a)
      Selection(c, rest) <- rest.chooseCard if a.suit == c.suit && ranking.pred(c, b)
      Selection(d, rest) <- rest.chooseCard if a.suit == d.suit && ranking.pred(d, c)
      Selection(e, rest) <- rest.chooseCard if a.suit == e.suit && ranking.pred(e, d)
    } yield Selection((a, b, c, d, e), rest)

  override def toString =
    s"Hand(${cards.mkString(",")})"
}

object Hand {

  /**
    * Order by the best "high card" selection.
    * Falls back to the second highest card if the highest is the same.
    * Etc...
    */
  val highestCard: Ordering[Hand] =
    Ordering.by(_.chooseCard)

  /**
    * Order by the best "single pair" selection.
    * Falls back to the second highest pair if the highest is the same.
    * Etc...
    */
  val singlePair: Ordering[Hand] =
    Ordering.by(_.choosePair)

  /**
    * Order by the best "two pairs" selection.
    */
  val twoPairs: Ordering[Hand] =
    Ordering.by(_.twoPairs)

  /**
    * Order by the best "three of a kind" selection.
    */
  val threeOfAKind: Ordering[Hand] =
    Ordering.by(_.threeOfAKind)

  /**
    * Order by the best "straight" selection.
    */
  val straight: Ordering[Hand] =
    Ordering.by((hand: Hand) => hand.straight(Card.acesHigh)) orElse
    Ordering.by((hand: Hand) => hand.straight(Card.acesLow))

  /**
    * Order by the best "flush" selection.
    */
  val flush: Ordering[Hand] =
    Ordering.by(_.flush)

  /**
    * Order by the best "full house" selection.
    */
  val fullHouse: Ordering[Hand] =
    Ordering.by(_.fullHouse)

  /**
    * Order by the best "four of a kind" selection.
    */
  val fourOfAKind: Ordering[Hand] =
    Ordering.by(_.fourOfAKind)

  /**
    * Order by the best "straight flush" selection.
    */
  val straightFlush: Ordering[Hand] =
    Ordering.by((hand: Hand) => hand.straightFlush(Card.acesHigh)) orElse
    Ordering.by((hand: Hand) => hand.straightFlush(Card.acesLow))

  /**
    * Order by the best selection of any type.
    */
  implicit val ordering: Ordering[Hand] =
    straightFlush orElse
    fourOfAKind orElse
    fullHouse orElse
    flush orElse
    straight orElse
    threeOfAKind orElse
    twoPairs orElse
    singlePair orElse
    highestCard
}
