package poker

import org.scalatest._
import org.scalatest.matchers._
import poker.syntax._

class HandSpec extends FreeSpec with Matchers {
  "hand orderings" - {
    "highest card" in {
      // One card wins:
      Hand.highestCard.compare(hand"2C 3S 4D 5H 6C", hand"2C 3S 4D 5H 7C") should be(-1)
      Hand.highestCard.compare(hand"2C 3S 4D 5H 7C", hand"2C 3S 4D 5H 6C") should be(+1)

      // Both cards the same:
      Hand.highestCard.compare(hand"2C 3S 4D 5H 6C", hand"2C 3S 4D 5H 6S") should be(0)
    }

    "single pair" in {
      // One pair wins:
      Hand.singlePair.compare(hand"2C 3S 4D 6H 6C", hand"2C 3S 4D 7H 7C") should be(-1)
      Hand.singlePair.compare(hand"2C 3S 4D 7H 7C", hand"2C 3S 4D 6H 6C") should be(+1)

      // Both pairs the same:
      Hand.singlePair.compare(hand"2C 3S 4D 6H 6C", hand"2C 3S 4D 6H 6S") should be(0)

      // Neither hand matches:
      Hand.singlePair.compare(hand"2C 3S 4D 5H 6C", hand"2C 3S 4D 5H 6S") should be(0)
    }

    "two pairs" in {
      // Highest pair different:
      Hand.twoPairs.compare(hand"2C 3S 3D 6H 6C", hand"2C 3S 3D 7H 7C") should be(-1)
      Hand.twoPairs.compare(hand"2C 3S 3D 7H 7C", hand"2C 3S 3D 6H 6C") should be(+1)

      // Lowest pair different:
      Hand.twoPairs.compare(hand"2C 3S 3D 6H 6C", hand"2C 4S 4D 6H 6C") should be(-1)
      Hand.twoPairs.compare(hand"2C 4S 4D 6H 6C", hand"2C 3S 3D 6H 6C") should be(+1)

      // Both pairs the same:
      Hand.twoPairs.compare(hand"2C 3S 3D 6H 6C", hand"2C 3S 3D 6H 6S") should be(0)

      // Neither hand matches:
      Hand.twoPairs.compare(hand"2C 3S 4D 5H 6C", hand"2C 3S 4D 5H 6S") should be(0)
    }

    "three of a kind" in {
      // Values differ:
      Hand.threeOfAKind.compare(hand"2C 3S 6D 6H 6C", hand"2C 3S 7D 7H 7C") should be(-1)
      Hand.threeOfAKind.compare(hand"2C 3S 7D 7H 7C", hand"2C 3S 6D 6H 6C") should be(+1)

      // One hand matches:
      Hand.threeOfAKind.compare(hand"2C 3S 3D 6H 6C", hand"2C 4S 6D 6H 6C") should be(-1)
      Hand.threeOfAKind.compare(hand"2C 4S 6D 6H 6C", hand"2C 3S 4D 6H 6C") should be(+1)

      // Neither hand matches:
      Hand.threeOfAKind.compare(hand"2C 3S 4D 5H 6C", hand"2C 3S 4D 5H 6S") should be(0)
    }

    "four of a kind" in {
      // Values differ:
      Hand.fourOfAKind.compare(hand"2C 6S 6D 6H 6C", hand"2C 7S 7D 7H 7C") should be(-1)
      Hand.fourOfAKind.compare(hand"2C 7S 7D 7H 7C", hand"2C 6S 6D 6H 6C") should be(+1)

      // One hand matches:
      Hand.fourOfAKind.compare(hand"2C 3S 3D 6H 6C", hand"2C 6S 6D 6H 6C") should be(-1)
      Hand.fourOfAKind.compare(hand"2C 6S 6D 6H 6C", hand"2C 3S 4D 6H 6C") should be(+1)

      // Neither hand matches:
      Hand.fourOfAKind.compare(hand"2C 3S 4D 5H 6C", hand"2C 3S 4D 5H 6S") should be(0)
    }

    "straight" - {
      "aces low" in {
        // Values differ:
        Hand.straight.compare(hand"AC 2S 3D 4H 5C", hand"2C 3S 4D 5H 6C") should be(-1)
        Hand.straight.compare(hand"2C 3S 4D 5H 6C", hand"AC 2S 3D 4H 5C") should be(+1)

        // One hand matches:
        Hand.straight.compare(hand"AC 2S 3D 4H 6C", hand"AC 2S 3D 4H 5C") should be(-1)
        Hand.straight.compare(hand"AC 2S 3D 4H 5C", hand"AC 2S 3D 4H 6C") should be(+1)

        // Neither hand matches:
        Hand.straight.compare(hand"AC 2S 3D 4H 6C", hand"AC 2S 3D 4H 7C") should be(0)
      }

      "aces high" in {
        // Values differ:
        Hand.straight.compare(hand"9C TC JH QS KD", hand"TC JH QS KD AC") should be(-1)
        Hand.straight.compare(hand"TC JH QS KD AC", hand"9C TC JH QS KD") should be(+1)

        // One hand matches:
        Hand.straight.compare(hand"9C JH QS KD AC", hand"TC JH QS KD AC") should be(-1)
        Hand.straight.compare(hand"TC JH QS KD AC", hand"9C JH QS KD AC") should be(+1)

        // Neither hand matches:
        Hand.straight.compare(hand"9C JH QS KD AC", hand"8C JH QS KD AC") should be(0)
      }

      "aces low vs aces high" in {
        Hand.straight.compare(hand"AC 2S 3D 4H 5C", hand"TC JH QS KD AC") should be(-1)
        Hand.straight.compare(hand"TC JH QS KD AC", hand"AC 2S 3D 4H 5C") should be(+1)
      }
    }

    "flush" in {
      // Values differ:
      Hand.flush.compare(hand"2H 3H 4H 5H 6H", hand"3H 4H 5H 6H 7H") should be(
        -1
      )
      Hand.flush.compare(hand"3H 4H 5H 6H 7H", hand"2H 3H 4H 5H 6H") should be(
        +1
      )

      // One hand matches:
      Hand.flush.compare(hand"AH 2H 3H 4H 6D", hand"AH 2H 3H 4H 5H") should be(
        -1
      )
      Hand.flush.compare(hand"AH 2H 3H 4H 5H", hand"AH 2H 3H 4H 6D") should be(
        +1
      )

      // Neither hand matches:
      Hand.flush.compare(hand"AH 2H 3H 4H 6D", hand"AH 2H 3H 4H 7D") should be(
        0
      )
    }

    "full house" in {
      // Highest cards different:
      Hand.fullHouse
        .compare(hand"2C 2S 6D 6H 6C", hand"2C 2S 7D 7H 7C") should be(-1)
      Hand.fullHouse
        .compare(hand"2C 2S 7D 7H 7C", hand"2C 2S 6D 6H 6C") should be(+1)
      Hand.fullHouse
        .compare(hand"2C 2S 2D 6H 6C", hand"2C 2S 2D 7H 7C") should be(-1)
      Hand.fullHouse
        .compare(hand"2C 2S 2D 7H 7C", hand"2C 2S 2D 6H 6C") should be(+1)

      // Lowest cards different:
      Hand.fullHouse
        .compare(hand"3C 3S 6D 6H 6C", hand"4C 4S 6D 6H 6C") should be(-1)
      Hand.fullHouse
        .compare(hand"4C 4S 6D 6H 6C", hand"3C 3S 6D 6H 6C") should be(+1)
      Hand.fullHouse
        .compare(hand"3C 3S 3D 6H 6C", hand"4C 4S 4D 6H 6C") should be(-1)
      Hand.fullHouse
        .compare(hand"4C 4S 4D 6H 6C", hand"3C 3S 3D 6H 6C") should be(+1)

      // High pair versus high three:
      Hand.fullHouse
        .compare(hand"3C 3S 3D 6H 6C", hand"3C 3S 6D 6H 6C") should be(-3)
      Hand.fullHouse
        .compare(hand"3C 3S 6D 6H 6C", hand"3C 3S 3D 6H 6C") should be(+3)
    }

    "straight flush" - {
      "aces low" in {
        // Values differ:
        Hand.straightFlush.compare(hand"AH 2H 3H 4H 5H", hand"2H 3H 4H 5H 6H") should be(-1)
        Hand.straightFlush.compare(hand"2H 3H 4H 5H 6H", hand"AH 2H 3H 4H 5H") should be(+1)

        // One hand matches:
        Hand.straightFlush.compare(hand"AH 2H 3H 4H 6H", hand"AH 2H 3H 4H 5H") should be(-1)
        Hand.straightFlush.compare(hand"AH 2H 3H 4H 5H", hand"AH 2H 3H 4H 6H") should be(+1)

        // Neither hand matches:
        Hand.straightFlush.compare(hand"AH 2H 3H 4H 6H", hand"AH 2H 3H 4H 7H") should be(0)
      }

      "aces high" in {
        // Values differ:
        Hand.straightFlush.compare(hand"9H TH JH QH KH", hand"TH JH QH KH AH") should be(-1)
        Hand.straightFlush.compare(hand"TH JH QH KH AH", hand"9H TH JH QH KH") should be(+1)

        // One hand matches:
        Hand.straightFlush.compare(hand"9H JH QH KH AH", hand"TH JH QH KH AH") should be(-1)
        Hand.straightFlush.compare(hand"TH JH QH KH AH", hand"9H JH QH KH AH") should be(+1)

        // Neither hand matches:
        Hand.straightFlush.compare(hand"9H JH QH KH AH", hand"8H JH QH KH AH") should be(0)
      }

      "aces low vs aces high" in {
        Hand.straightFlush.compare(hand"AH 2H 3H 4H 5H", hand"TH JH QH KH AH") should be(-1)
        Hand.straightFlush.compare(hand"TH JH QH KH AH", hand"AH 2H 3H 4H 5H") should be(+1)
      }
    }
  }

  "Hand ordering" - {
    def beat(r: Hand): Matcher[Hand] =
      Matcher(
        l =>
          MatchResult(
            Hand.ordering.compare(l, r) > 0 && Hand.ordering.compare(r, l) < 0,
            s"$l does not beat $r",
            s"$l beats $r"
          )
      )

    def tie(r: Hand): Matcher[Hand] =
      Matcher(
        l =>
          MatchResult(
            Hand.ordering.compare(l, r) == 0 && Hand.ordering.compare(r, l) == 0,
            s"$l does not tie $r",
            s"$l ties $r"
          )
      )

    "no winning hand" in {
      hand"2C 3D 5H 6S 7D" should tie(hand"2C 3D 5H 6S 7C")
    }

    "high card wins" in {
      hand"2C 3D 5H 6S 8D" should beat(hand"2C 3D 5H 6S 7C")
    }

    "second high card wins" in {
      hand"2C 3D 5H 7S 8D" should beat(hand"2C 3D 5H 6S 8C")
    }

    "single pair" - {
      "highest pair wins" in {
        hand"2C 3D 5H 5S 7C" should beat(hand"2C 3D 3H 6S 7D")
      }

      "same pair goes to highest card" in {
        hand"2C 3D 3H 6S 8D" should beat(hand"2C 3S 3C 6S 7C")
      }

      "same pair and same highest card stalemates" in {
        hand"2C 3D 3H 6S 7D" should tie(hand"2C 3S 3C 6S 7C")
      }
    }

    "two pairs" - {
      "two pairs beats single pair" in {
        hand"2C 3D 3H 6S 6D" should beat(hand"2C 3S 3C 6S 7C")
      }

      "highest higher pair wins" in {
        hand"2C 3S 3C 7S 7C" should beat(hand"2C 3D 3H 6S 6D")
      }

      "highest lower pair wins" in {
        hand"2C 4S 4C 6S 6C" should beat(hand"2C 3D 3H 6S 6D")
      }

      "same pair stalemate" in {
        hand"2C 3D 3H 6S 7D" should tie(hand"2C 3S 3C 6S 7C")
      }

      "same two pairs goes to highest card" in {
        hand"4C 3D 3H 6S 6D" should beat(hand"2C 3S 3C 6S 6C")
      }
    }

    "three of a kind" - {
      "three of a kind beats two pairs" in {
        hand"2C 3D 6H 6S 6D" should beat(hand"2C 3S 3D 7S 7C")
      }

      "highest three wins" in {
        hand"2C 3S 7D 7S 7C" should beat(hand"2C 3D 6H 6S 6D")
      }

      "same three stalemate" in {
        hand"2C 3D 3H 3S 7D" should tie(hand"2C 3D 3C 3S 7C")
      }

      "same three goes to highest card" in {
        hand"4C 3D 3H 3S 6D" should beat(hand"2C 3D 3C 3S 6C")
      }
    }

    "straight" - {
      "straight beats three of a kind" in {
        hand"2C 3C 4C 5C 6D" should beat(hand"2C 3C 7D 7H 7S")
      }

      "highest straight wins" in {
        hand"2C 3C 4C 5C 6D" should beat(hand"AC 2C 3C 4C 5D")
      }

      "aces high beats aces low" in {
        hand"AC KC QC JC TD" should beat(hand"AC 2C 3C 4C 5D")
      }

      "same straight stalemate" in {
        hand"AC KC QC JC TD" should tie(hand"AS KS QS JS TH")
      }
    }

    "flush" - {
      "flush beats straight" in {
        hand"2C 3C 4C 5C 7C" should beat(hand"2C 3C 7D 7H 7S")
      }

      "highest flush wins" in {
        hand"AC 2C 3C 4C 6C" should beat(hand"2C 3C 4C 5C 7C")
      }

      "same flush stalemate" in {
        hand"AC KC QC JC 9D" should tie(hand"AS KS QS JS 9H")
      }
    }

    "full house" - {
      "full house beats flush" in {
        hand"2C 2D 3C 3D 3H" should beat(hand"2C 3C 4C 5C 7C")
      }

      "highest three wins" in {
        hand"2C 2D 3C 3D 3H" should beat(hand"3C 3D 2C 2D 2H")
      }
    }

    "four of a kind" - {
      "four of a kind beats full house" in {
        hand"2C 6C 6H 6S 6D" should beat(hand"2C 2S 7D 7S 7C")
      }

      "highest four wins" in {
        hand"2C 7H 7D 7S 7C" should beat(hand"2C 3D 6H 6S 6C")
      }

      "same four stalemate" in {
        hand"2C 7H 7D 7S 7C" should tie(hand"2C 7H 7D 7S 7C")
      }

      "same four goes to highest card" in {
        hand"3C 7H 7D 7S 7C" should beat(hand"2C 7H 7D 7S 7C")
      }
    }

    "straight flush" - {
      "straight flush beats four of a kind" in {
        hand"2C 3C 4C 5C 6C" should beat(hand"2C 7C 7D 7H 7S")
      }

      "highest straight flush wins" in {
        hand"AC KC QC JC TC" should beat(hand"2C 3C 4C 5C 6C")
      }

      "same straight flush stalemate" in {
        hand"AC 2C 3C 4C 5C" should tie(hand"AD 2D 3D 4D 5D")
      }
    }
  }
}
