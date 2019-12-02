package poker

import org.scalatest._
import poker.syntax._

class PokerSpec extends FreeSpec with Matchers {
  "example 1 from the readme" in {
    pending
    val handA = hand"2H 3D 5S 9C KD"
    val handB = hand"2C 3H 4S 8C AH"
    Poker.compare(handA, handB) shouldBe -1 // hand 2 wins
  }

  "example 2 from the readme" in {
    pending
    val handA = hand"2H 4S 4C 2D 4H"
    val handB = hand"2S 8S AS QS 3S"
    Poker.compare(handA, handB) shouldBe +1 // hand 1 wins
  }

  "example 3 from the readme" in {
    pending
    val handA = hand"2H 3D 5S 9C KD"
    val handB = hand"2C 3H 4S 8C KH"
    Poker.compare(handA, handB) shouldBe +1 // hand 1 wins
  }

  "example 4 from the readme" in {
    pending
    val handA = hand"2H 3D 5S 9C KD"
    val handB = hand"2D 3H 5C 9S KH"
    Poker.compare(handA, handB) shouldBe 0 // tie
  }
}
