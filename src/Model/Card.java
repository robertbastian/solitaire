package Solitaire.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/* Models a playing card */

public class Card {

  /* Abs: (1) number = 1 => card is an Ace of 'suit
          (2) 2 <= number <= 10 => card is a 'number' of 'suit'
          (3) number = 11 => card is a Jack of 'suit'
          (4) number = 12 => card is a Queen of 'suit'
          (5) number = 13 => card is a King of 'suit' */

  /* DTI: (1) 1 <= number <= suitSize 
          (2) color = suit = Suit.Clubs || suit. Suit.Spades */

  public final static int suitSize = 13;
  public enum Suit { Clubs, Diamonds, Hearts, Spades }

  public final int number;
  public final Suit suit;
  public final boolean color;

  // Constructor private because cards can only be obtained as part of a deck
  private Card(Suit s, int n) {
    assert n >= 1 && n <= suitSize;
    suit = s; number = n;
    color = suit == Suit.Clubs || suit == Suit.Spades;
  }

  // Post: returns [Card(s,n) | s <- Suits, n <- [1..13]]
  public static List<Card> fullDeck(){
    List<Card> deck = new ArrayList<Card>(52);
    for (Suit suit : Suit.values()) {
      for (int cardnumber = 1; cardnumber <= suitSize; cardnumber++)
        deck.add(new Card(suit, cardnumber));
    }
    return deck;
  }

  // Post: returns fullDeck() shuffled using the Fischer-Yates algorithm
  public static List<Card> shuffledDeck(){
    List<Card> deck = fullDeck();
    Random rgen = new Random();
    for (int n = deck.size(); n > 1; n--){
      int random = (int) (n * rgen.nextFloat());
      // Switching deck[random] with deck[n-1]
      Card d = deck.get(random);
      deck.set(random, deck.get(n-1));
      deck.set(n-1,d);
    }
    return deck;
  }

  /* Needed to use cards as keys in HashMap */
  @Override
  public int hashCode(){
    return (suit.hashCode() ^ number);
  }

  @Override
  public boolean equals(Object o){
    if (o == null || !(o instanceof Card))
      return false;
    Card c = (Card) o;
    return suit == c.suit && number == c.number;
  }
}
