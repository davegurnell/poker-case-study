# Poker Hand Comparison Case Study

Scala case study about identifying and comparing scoring poker hands.
Based on a Kata in the [Coding Dojo Handbook](https://leanpub.com/codingdojohandbook)
by Emily Bache.

Licensed [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Problem Description

Your job is to compare several pairs of poker hands 
and to indicate which, if either, has a higher rank.

Poker rules description

A poker deck contains 52 cards. 
Each card has a suit which is one of 
clubs, diamonds, hearts, or spades 
(denoted C, D, H, and S in the input data). 
Each card also has a value which is one of 
2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace 
(denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A). 

For scoring purposes, 
the suits are unordered while the values are ordered as given above, 
with 2 being the lowest and ace the highest value.

A poker hand consists of 5 cards dealt from the deck. 
Poker hands are ranked by the following partial order 
from lowest to highest.

 - High Card: Hands which do not fit any higher category are ranked by the value of their highest card. If the highest cards have the same value, the hands are ranked by the next highest, and so on.
 - Pair: 2 of the 5 cards in the hand have the same value. Hands which both contain a pair are ranked by the value of the cards forming the pair. If these values are the same, the hands are ranked by the values of the cards not forming the pair, in decreasing order.
 - Two Pairs: The hand contains 2 different pairs. Hands which both contain 2 pairs are ranked by the value of their highest pair. Hands with the same highest pair are ranked by the value of their other pair. If these values are the same the hands are ranked by the value of the remaining card.
 - Three of a Kind: Three of the cards in the hand have the same value. Hands which both contain three of a kind are ranked by the value of the 3 cards.
 - Straight: Hand contains 5 cards with consecutive values. Hands which both contain a straight are ranked by their highest card.
 - Flush: Hand contains 5 cards of the same suit. Hands which are both flushes are ranked using the rules for High Card.
 - Full House: 3 cards of the same value, with the remaining 2 cards forming a pair. Ranked by the value of the 3 cards.
 - Four of a kind: 4 cards with the same value. Ranked by the value of the 4 cards.
 - Straight flush: 5 cards of the same suit with consecutive values. Ranked by the highest card in the hand.

## Suggested Test Cases

These are recreated for you in `Main.scala` and `PokerSpec.scala`:

 - Hand 1: 2H 3D 5S 9C KD<br>
   Hand 2: 2C 3H 4S 8C AH<br>
   Hand 2 wins. - with high card: Ace
  
 - Hand 1: 2H 4S 4C 2D 4H<br>
   Hand 2: 2S 8S AS QS 3S<br>
   Hand 1 wins. - with full house: 4 over 2
  
 - Hand 1: 2H 3D 5S 9C KD<br>
   Hand 2: 2C 3H 4S 8C KH<br>
   Hand 1 wins. - with high card: 9
  
 - Hand 1: 2H 3D 5S 9C KD<br>
   Hand 2: 2D 3H 5C 9S KH<br>
   Tie.

## Tips

 - Don't try to assign scores to particular combinations of cards.
   Instead concentrate 100% on whether one hand beats another.
 
 - Each rule can be encoded as a function similar to a `Comparator` or `Ordering`:
 
    ```scala
    (Hand, Hand) => Int // -1, 0, or 1
    ```

 - You can encode each rule from the bullet list above as a function of this type.
   Concentrate on one rule at a time, then work out how to combine them.
   
 ## Starting Code
 
We've given you some code to start with.
Keep this, change it, or discard it as you see fit:
 
 - A `Poker` object containing a top-level `compare` method
 
 - Classes representing a `Card`, a `Hand`, a face `Value`, and a `Suit`

 - An object `poker.syntax` containing string fancy syntax for defining cards and hands:
    
    ```scala
    import poker.syntax._
      
    card"AD" // Card(Value.Ace, Suit.Diamond)
    hand"2H 3D 5S 9C KD" // Hand(List(Card(Value.Two, Suit.Heart), ...))
    ```
