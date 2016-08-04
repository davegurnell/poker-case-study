package poker

import poker.syntax._
import org.scalatest._

class CardSpec extends FreeSpec with Matchers {
  "acesHigh" - {
    "determine successors" in {
      Card.acesHigh.pred(c"2C", c"3C") should be(true)
      Card.acesHigh.pred(c"2C", c"2C") should be(false)
      Card.acesHigh.pred(c"3C", c"2C") should be(false)
    }

    "treats aces high" in {
      Card.acesHigh.pred(c"KC", c"AC") should be(true)
      Card.acesHigh.pred(c"AC", c"KC") should be(false)

      Card.acesHigh.pred(c"2C", c"AC") should be(false)
      Card.acesHigh.pred(c"AC", c"2C") should be(false)
    }

    "does not discrimate on suit" in {
      Card.acesHigh.pred(c"2C", c"2D") should be(false)
      Card.acesHigh.pred(c"2D", c"2C") should be(false)
    }
  }

  "acesLow" - {
    "sorts by value" in {
      Card.acesLow.pred(c"2C", c"3C") should be(true)
      Card.acesLow.pred(c"2C", c"2C") should be(false)
      Card.acesLow.pred(c"3C", c"2C") should be(false)
    }

    "treats aces low" in {
      Card.acesLow.pred(c"KC", c"AC") should be(false)
      Card.acesLow.pred(c"AC", c"KC") should be(false)

      Card.acesLow.pred(c"2C", c"AC") should be(false)
      Card.acesLow.pred(c"AC", c"2C") should be(true)
    }

    "does not discrimate on suit" in {
      Card.acesLow.pred(c"2C", c"2D") should be(false)
      Card.acesLow.pred(c"2D", c"2C") should be(false)
    }
  }

  "ordering" - {
    "sorts by ascending value" in {
      Card.ordering.compare(c"2C", c"3C") should be(-1)
      Card.ordering.compare(c"2C", c"2C") should be( 0)
      Card.ordering.compare(c"3C", c"2C") should be(+1)
    }

    "treats aces high" in {
      Card.ordering.compare(c"KC", c"AC") should be(-1)
      Card.ordering.compare(c"AC", c"KC") should be(+1)
    }

    "does not discrimate on suit" in {
      Card.ordering.compare(c"2C", c"2D") should be(0)
      Card.ordering.compare(c"2D", c"2C") should be(0)
    }

    "is the default ordering" in {
      val cards = List(c"KC", c"AC")
      cards.sorted should be(cards)
      cards.reverse.sorted should be(cards)
    }
  }
}