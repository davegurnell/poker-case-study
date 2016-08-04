package poker

import poker.syntax._
import org.scalatest._

class ValueSpec extends FreeSpec with Matchers {
  "acesHigh" - {
    "determines predecessors" in {
      Value.acesHigh.pred( Two,  Four) should be(false)
      Value.acesHigh.pred( Two, Three) should be(true)
      Value.acesHigh.pred( Two,   Two) should be(false)
      Value.acesHigh.pred( Two,   Ace) should be(false)
    }

    "treats aces high" in {
      Value.acesHigh.pred( Ace,  Two) should be(false)
      Value.acesHigh.pred( Ace,  Ace) should be(false)
      Value.acesHigh.pred( Ace, King) should be(false)
      Value.acesHigh.pred( Two,  Ace) should be(false)
      Value.acesHigh.pred( Ace,  Ace) should be(false)
      Value.acesHigh.pred(King,  Ace) should be(true)
    }
  }

  "acesLow" - {
    "determines predecessors" in {
      Value.acesLow.pred( Two,  Four) should be(false)
      Value.acesLow.pred( Two, Three) should be(true)
      Value.acesLow.pred( Two,   Two) should be(false)
      Value.acesLow.pred( Two,   Ace) should be(false)
    }

    "treats aces low" in {
      Value.acesLow.pred( Ace,  Two) should be(true)
      Value.acesLow.pred( Ace,  Ace) should be(false)
      Value.acesLow.pred( Ace, King) should be(false)
      Value.acesLow.pred( Two,  Ace) should be(false)
      Value.acesLow.pred( Ace,  Ace) should be(false)
      Value.acesLow.pred(King,  Ace) should be(false)
    }
  }
}