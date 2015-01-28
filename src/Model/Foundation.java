package Solitaire.Model;

import java.util.List;

/* Models a Foundation stack, with the DTI enforcing the rules of the game */

public class Foundation extends Stack {

  /* 
    Additions to Stack's specification:
    DTI: (1) 0 <= i < j < size => cards[i].suit = cards[j].suit
         (2) 0 <= i < size => cards[i].number = i + 1 
  */

  @Override
  // Post: returns the number of visible cards, at most two (if top card is being dragged, two cards are visible)
  public int visible(){
    return Math.min(size(), 2);
  }

  @Override
  // Pre: n > 0
  // Post: returns whether removing n cards from this stack is a valid move according to the rules of the game
  public boolean validRemove(int n){
    assert n > 0;
    return n == 1 && size() > 0;
  }

  @Override
  // Pre: cs != null && cs != []
  // Post: returns whether adding cs to cards is a valid move according to the rules of the game 
  public boolean validAdd(List<Card> cs){
    assert cs != null && cs.size() > 0;
    Card c = cs.get(0);

    // Checking DTI.1: if not empty, suit has to match and cs has to only be one card
    if (size() != 0 && (c.suit != cards.get(0).suit || cs.size() > 1))
      return false;

    // Checking DTI.2: has to be the right number
    return c.number == size() + 1;
  }
}