package poker

import poker.syntax._

object Main extends App {
  val hand1a = hand"2H 3D 5S 9C KD"
  val hand1b = hand"2C 3H 4S 8C AH"
  testCase("Test 1", hand1a, hand1b)

  val hand2a = hand"2H 4S 4C 2D 4H"
  val hand2b = hand"2S 8S AS QS 3S"
  testCase("Test 2", hand2a, hand2b)

  val hand3a = hand"2H 3D 5S 9C KD"
  val hand3b = hand"2C 3H 4S 8C KH"
  testCase("Test 3", hand3a, hand3b)

  val hand4a = hand"2H 3D 5S 9C KD"
  val hand4b = hand"2D 3H 5C 9S KH"
  testCase("Test 4", hand4a, hand4b)

  def testCase(name: String, hand1: Hand, hand2: Hand): Int = {
    val ans = Poker.compare(hand1, hand1)
    println(s"Test case $name: $hand1 / $hand1 => $ans")
    ans
  }
}
