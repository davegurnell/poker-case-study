package poker

import poker.syntax._
import org.scalatest._

class CardSpec extends FreeSpec with Matchers {
  "acesHigh" - {
    "determine successors" in {
      Card.acesHigh.pred(card"2C", card"3C") should be(true)
      Card.acesHigh.pred(card"2C", card"2C") should be(false)
      Card.acesHigh.pred(card"3C", card"2C") should be(false)
    }

    "treats aces high" in {
      Card.acesHigh.pred(card"KC", card"AC") should be(true)
      Card.acesHigh.pred(card"AC", card"KC") should be(false)

      Card.acesHigh.pred(card"2C", card"AC") should be(false)
      Card.acesHigh.pred(card"AC", card"2C") should be(false)
    }

    "does not discrimate on suit" in {
      Card.acesHigh.pred(card"2C", card"2D") should be(false)
      Card.acesHigh.pred(card"2D", card"2C") should be(false)
    }
  }

  "acesLow" - {
    "sorts by value" in {
      Card.acesLow.pred(card"2C", card"3C") should be(true)
      Card.acesLow.pred(card"2C", card"2C") should be(false)
      Card.acesLow.pred(card"3C", card"2C") should be(false)
    }

    "treats aces low" in {
      Card.acesLow.pred(card"KC", card"AC") should be(false)
      Card.acesLow.pred(card"AC", card"KC") should be(false)

      Card.acesLow.pred(card"2C", card"AC") should be(false)
      Card.acesLow.pred(card"AC", card"2C") should be(true)
    }

    "does not discrimate on suit" in {
      Card.acesLow.pred(card"2C", card"2D") should be(false)
      Card.acesLow.pred(card"2D", card"2C") should be(false)
    }
  }

  "ordering" - {
    "sorts by ascending value" in {
      Card.ordering.compare(card"2C", card"3C") should be(-1)
      Card.ordering.compare(card"2C", card"2C") should be(0)
      Card.ordering.compare(card"3C", card"2C") should be(+1)
    }

    "treats aces high" in {
      Card.ordering.compare(card"KC", card"AC") should be(-1)
      Card.ordering.compare(card"AC", card"KC") should be(+1)
    }

    "does not discrimate on suit" in {
      Card.ordering.compare(card"2C", card"2D") should be(0)
      Card.ordering.compare(card"2D", card"2C") should be(0)
    }

    "is the default ordering" in {
      val cards = List(card"KC", card"AC")
      cards.sorted should be(cards)
      cards.reverse.sorted should be(cards)
    }
  }
}
