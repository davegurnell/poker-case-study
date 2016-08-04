package poker

import org.scalatest._
import org.scalatest.matchers._
import poker.syntax._

class HandSpec extends FreeSpec with Matchers {
  "hand orderings" - {
    "highest card" in {
      // One card wins:
      Hand.highestCard.compare(h"2C 3S 4D 5H 6C", h"2C 3S 4D 5H 7C") should be(-1)
      Hand.highestCard.compare(h"2C 3S 4D 5H 7C", h"2C 3S 4D 5H 6C") should be(+1)

      // Both cards the same:
      Hand.highestCard.compare(h"2C 3S 4D 5H 6C", h"2C 3S 4D 5H 6S") should be( 0)
    }

    "single pair" in {
      // One pair wins:
      Hand.singlePair.compare(h"2C 3S 4D 6H 6C", h"2C 3S 4D 7H 7C") should be(-1)
      Hand.singlePair.compare(h"2C 3S 4D 7H 7C", h"2C 3S 4D 6H 6C") should be(+1)

      // Both pairs the same:
      Hand.singlePair.compare(h"2C 3S 4D 6H 6C", h"2C 3S 4D 6H 6S") should be( 0)

      // Neither hand matches:
      Hand.singlePair.compare(h"2C 3S 4D 5H 6C", h"2C 3S 4D 5H 6S") should be( 0)
    }

    "two pairs" in {
      // Highest pair different:
      Hand.twoPairs.compare(h"2C 3S 3D 6H 6C", h"2C 3S 3D 7H 7C") should be(-1)
      Hand.twoPairs.compare(h"2C 3S 3D 7H 7C", h"2C 3S 3D 6H 6C") should be(+1)

      // Lowest pair different:
      Hand.twoPairs.compare(h"2C 3S 3D 6H 6C", h"2C 4S 4D 6H 6C") should be(-1)
      Hand.twoPairs.compare(h"2C 4S 4D 6H 6C", h"2C 3S 3D 6H 6C") should be(+1)

      // Both pairs the same:
      Hand.twoPairs.compare(h"2C 3S 3D 6H 6C", h"2C 3S 3D 6H 6S") should be( 0)

      // Neither hand matches:
      Hand.twoPairs.compare(h"2C 3S 4D 5H 6C", h"2C 3S 4D 5H 6S") should be( 0)
    }

    "three of a kind" in {
      // Values differ:
      Hand.threeOfAKind.compare(h"2C 3S 6D 6H 6C", h"2C 3S 7D 7H 7C") should be(-1)
      Hand.threeOfAKind.compare(h"2C 3S 7D 7H 7C", h"2C 3S 6D 6H 6C") should be(+1)

      // One hand matches:
      Hand.threeOfAKind.compare(h"2C 3S 3D 6H 6C", h"2C 4S 6D 6H 6C") should be(-1)
      Hand.threeOfAKind.compare(h"2C 4S 6D 6H 6C", h"2C 3S 4D 6H 6C") should be(+1)

      // Neither hand matches:
      Hand.threeOfAKind.compare(h"2C 3S 4D 5H 6C", h"2C 3S 4D 5H 6S") should be( 0)
    }

    "four of a kind" in {
      // Values differ:
      Hand.fourOfAKind.compare(h"2C 6S 6D 6H 6C", h"2C 7S 7D 7H 7C") should be(-1)
      Hand.fourOfAKind.compare(h"2C 7S 7D 7H 7C", h"2C 6S 6D 6H 6C") should be(+1)

      // One hand matches:
      Hand.fourOfAKind.compare(h"2C 3S 3D 6H 6C", h"2C 6S 6D 6H 6C") should be(-1)
      Hand.fourOfAKind.compare(h"2C 6S 6D 6H 6C", h"2C 3S 4D 6H 6C") should be(+1)

      // Neither hand matches:
      Hand.fourOfAKind.compare(h"2C 3S 4D 5H 6C", h"2C 3S 4D 5H 6S") should be( 0)
    }

    "straight" - {
      "aces low" in {
        // Values differ:
        Hand.straight.compare(h"AC 2S 3D 4H 5C", h"2C 3S 4D 5H 6C") should be(-1)
        Hand.straight.compare(h"2C 3S 4D 5H 6C", h"AC 2S 3D 4H 5C") should be(+1)

        // One hand matches:
        Hand.straight.compare(h"AC 2S 3D 4H 6C", h"AC 2S 3D 4H 5C") should be(-1)
        Hand.straight.compare(h"AC 2S 3D 4H 5C", h"AC 2S 3D 4H 6C") should be(+1)

        // Neither hand matches:
        Hand.straight.compare(h"AC 2S 3D 4H 6C", h"AC 2S 3D 4H 7C") should be( 0)
      }

      "aces high" in {
        // Values differ:
        Hand.straight.compare(h"9C TC JH QS KD", h"TC JH QS KD AC") should be(-1)
        Hand.straight.compare(h"TC JH QS KD AC", h"9C TC JH QS KD") should be(+1)

        // One hand matches:
        Hand.straight.compare(h"9C JH QS KD AC", h"TC JH QS KD AC") should be(-1)
        Hand.straight.compare(h"TC JH QS KD AC", h"9C JH QS KD AC") should be(+1)

        // Neither hand matches:
        Hand.straight.compare(h"9C JH QS KD AC", h"8C JH QS KD AC") should be( 0)
      }

      "aces low vs aces high" in {
        Hand.straight.compare(h"AC 2S 3D 4H 5C", h"TC JH QS KD AC") should be(-1)
        Hand.straight.compare(h"TC JH QS KD AC", h"AC 2S 3D 4H 5C") should be(+1)
      }
    }

    "flush" in {
      // Values differ:
      Hand.flush.compare(h"2H 3H 4H 5H 6H", h"3H 4H 5H 6H 7H") should be(-1)
      Hand.flush.compare(h"3H 4H 5H 6H 7H", h"2H 3H 4H 5H 6H") should be(+1)

      // One hand matches:
      Hand.flush.compare(h"AH 2H 3H 4H 6D", h"AH 2H 3H 4H 5H") should be(-1)
      Hand.flush.compare(h"AH 2H 3H 4H 5H", h"AH 2H 3H 4H 6D") should be(+1)

      // Neither hand matches:
      Hand.flush.compare(h"AH 2H 3H 4H 6D", h"AH 2H 3H 4H 7D") should be( 0)
    }

    "full house" in {
      // Highest cards different:
      Hand.fullHouse.compare(h"2C 2S 6D 6H 6C", h"2C 2S 7D 7H 7C") should be(-1)
      Hand.fullHouse.compare(h"2C 2S 7D 7H 7C", h"2C 2S 6D 6H 6C") should be(+1)
      Hand.fullHouse.compare(h"2C 2S 2D 6H 6C", h"2C 2S 2D 7H 7C") should be(-1)
      Hand.fullHouse.compare(h"2C 2S 2D 7H 7C", h"2C 2S 2D 6H 6C") should be(+1)

      // Lowest cards different:
      Hand.fullHouse.compare(h"3C 3S 6D 6H 6C", h"4C 4S 6D 6H 6C") should be(-1)
      Hand.fullHouse.compare(h"4C 4S 6D 6H 6C", h"3C 3S 6D 6H 6C") should be(+1)
      Hand.fullHouse.compare(h"3C 3S 3D 6H 6C", h"4C 4S 4D 6H 6C") should be(-1)
      Hand.fullHouse.compare(h"4C 4S 4D 6H 6C", h"3C 3S 3D 6H 6C") should be(+1)

      // High pair versus high three:
      Hand.fullHouse.compare(h"3C 3S 3D 6H 6C", h"3C 3S 6D 6H 6C") should be(-1)
      Hand.fullHouse.compare(h"3C 3S 6D 6H 6C", h"3C 3S 3D 6H 6C") should be(+1)
    }

    "straight flush" - {
      "aces low" in {
        // Values differ:
        Hand.straightFlush.compare(h"AH 2H 3H 4H 5H", h"2H 3H 4H 5H 6H") should be(-1)
        Hand.straightFlush.compare(h"2H 3H 4H 5H 6H", h"AH 2H 3H 4H 5H") should be(+1)

        // One hand matches:
        Hand.straightFlush.compare(h"AH 2H 3H 4H 6H", h"AH 2H 3H 4H 5H") should be(-1)
        Hand.straightFlush.compare(h"AH 2H 3H 4H 5H", h"AH 2H 3H 4H 6H") should be(+1)

        // Neither hand matches:
        Hand.straightFlush.compare(h"AH 2H 3H 4H 6H", h"AH 2H 3H 4H 7H") should be( 0)
      }

      "aces high" in {
        // Values differ:
        Hand.straightFlush.compare(h"9H TH JH QH KH", h"TH JH QH KH AH") should be(-1)
        Hand.straightFlush.compare(h"TH JH QH KH AH", h"9H TH JH QH KH") should be(+1)

        // One hand matches:
        Hand.straightFlush.compare(h"9H JH QH KH AH", h"TH JH QH KH AH") should be(-1)
        Hand.straightFlush.compare(h"TH JH QH KH AH", h"9H JH QH KH AH") should be(+1)

        // Neither hand matches:
        Hand.straightFlush.compare(h"9H JH QH KH AH", h"8H JH QH KH AH") should be( 0)
      }

      "aces low vs aces high" in {
        Hand.straightFlush.compare(h"AH 2H 3H 4H 5H", h"TH JH QH KH AH") should be(-1)
        Hand.straightFlush.compare(h"TH JH QH KH AH", h"AH 2H 3H 4H 5H") should be(+1)
      }
    }
  }

  "Hand ordering" - {
    def beat(r: Hand): Matcher[Hand] =
      Matcher(l => MatchResult((l beats r) && !(r beats l), s"$l does not beat $r", s"$l beats $r"))

    def tie(r: Hand): Matcher[Hand] =
      Matcher(l => MatchResult(!(l beats r) && !(r beats l), s"$l does not tie $r", s"$l ties $r"))

    "no winning hand" in {
      h"2C 3D 5H 6S 7D" should tie(h"2C 3D 5H 6S 7C")
    }

    "high card wins" in {
      h"2C 3D 5H 6S 8D" should beat(h"2C 3D 5H 6S 7C")
    }

    "second high card wins" in {
      h"2C 3D 5H 7S 8D" should beat(h"2C 3D 5H 6S 8C")
    }

    "single pair" - {
      "highest pair wins" in {
        h"2C 3D 5H 5S 7C" should beat(h"2C 3D 3H 6S 7D")
      }

      "same pair goes to highest card" in {
        h"2C 3D 3H 6S 8D" should beat(h"2C 3S 3C 6S 7C")
      }

      "same pair and same highest card stalemates" in {
        h"2C 3D 3H 6S 7D" should tie(h"2C 3S 3C 6S 7C")
      }
    }

    "two pairs" - {
      "two pairs beats single pair" in {
        h"2C 3D 3H 6S 6D" should beat(h"2C 3S 3C 6S 7C")
      }

      "highest higher pair wins" in {
        h"2C 3S 3C 7S 7C" should beat(h"2C 3D 3H 6S 6D")
      }

      "highest lower pair wins" in {
        h"2C 4S 4C 6S 6C" should beat(h"2C 3D 3H 6S 6D")
      }

      "same pair stalemate" in {
        h"2C 3D 3H 6S 7D" should tie(h"2C 3S 3C 6S 7C")
      }

      "same two pairs goes to highest card" in {
        h"4C 3D 3H 6S 6D" should beat(h"2C 3S 3C 6S 6C")
      }
    }

    "three of a kind" - {
      "three of a kind beats two pairs" in {
        h"2C 3D 6H 6S 6D" should beat(h"2C 3S 3D 7S 7C")
      }

      "highest three wins" in {
        h"2C 3S 7D 7S 7C" should beat(h"2C 3D 6H 6S 6D")
      }

      "same three stalemate" in {
        h"2C 3D 3H 3S 7D" should tie(h"2C 3D 3C 3S 7C")
      }

      "same three goes to highest card" in {
        h"4C 3D 3H 3S 6D" should beat(h"2C 3D 3C 3S 6C")
      }
    }

    "straight" - {
      "straight beats three of a kind" in {
        h"2C 3C 4C 5C 6D" should beat(h"2C 3C 7D 7H 7S")
      }

      "highest straight wins" in {
        h"2C 3C 4C 5C 6D" should beat(h"AC 2C 3C 4C 5D")
      }

      "aces high beats aces low" in {
        h"AC KC QC JC TD" should beat(h"AC 2C 3C 4C 5D")
      }

      "same straight stalemate" in {
        h"AC KC QC JC TD" should tie(h"AS KS QS JS TH")
      }
    }

    "flush" - {
      "flush beats straight" in {
        h"2C 3C 4C 5C 7C" should beat(h"2C 3C 7D 7H 7S")
      }

      "highest flush wins" in {
        h"AC 2C 3C 4C 6C" should beat(h"2C 3C 4C 5C 7C")
      }

      "same flush stalemate" in {
        h"AC KC QC JC 9D" should tie(h"AS KS QS JS 9H")
      }
    }

    "full house" - {
      "full house beats flush" in {
        h"2C 2D 3C 3D 3H" should beat(h"2C 3C 4C 5C 7C")
      }

      "highest three wins" in {
        h"2C 2D 3C 3D 3H" should beat(h"3C 3D 2C 2D 2H")
      }
    }

    "four of a kind" - {
      "four of a kind beats full house" in {
        h"2C 6C 6H 6S 6D" should beat(h"2C 2S 7D 7S 7C")
      }

      "highest four wins" in {
        h"2C 7H 7D 7S 7C" should beat(h"2C 3D 6H 6S 6C")
      }

      "same four stalemate" in {
        h"2C 7H 7D 7S 7C" should tie(h"2C 7H 7D 7S 7C")
      }

      "same four goes to highest card" in {
        h"3C 7H 7D 7S 7C" should beat(h"2C 7H 7D 7S 7C")
      }
    }

    "straight flush" - {
      "straight flush beats four of a kind" in {
        h"2C 3C 4C 5C 6C" should beat(h"2C 7C 7D 7H 7S")
      }

      "highest straight flush wins" in {
        h"AC KC QC JC TC" should beat(h"2C 3C 4C 5C 6C")
      }

      "same straight flush stalemate" in {
        h"AC 2C 3C 4C 5C" should tie(h"AD 2D 3D 4D 5D")
      }
    }
  }
}
