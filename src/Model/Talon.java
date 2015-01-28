package Solitaire.Model;

import java.util.List;
import java.util.ArrayList;

/* Models a Talon and the associated Waste */

public class Talon extends Stack {

  /* 
    Additions to Stack's specification: 
    Abs: (1) Talon = talon 
         (2) Waste = Stack (= cards)
         (3) 0 <= i < j talon.size => talon[i] is conceptually ABOVE talon[j]
    DTI: (1) talon != null 
  */
  
  private List<Card> talon;

  // Post: talon = cs && waste = []
  public Talon(List<Card> cs){
    talon = new ArrayList<Card>(cs);
  }

    // Post: returns the number of visible cards of the waste
  public int visible(){
    return Math.min(3,size());
  }

  // Post: returns talon == []
  public boolean talonEmpty(){
    return talon.size() == 0;
  }

  // Post: if talon != []: talon = drop 3 talon0 && waste = waste0 ++ take 3 talon
  //       else: waste = talon0 && talon = waste0
  public void reveal(){
    // If talon is empty, switch talon and waste (this works because the lists grow in opposite directions)
    if (talonEmpty()){
      List<Card> d = talon;
      talon = cards;
      cards = d;
    }
    // Otherwise, reveal up to three cards
    else 
      for (int i = 0; i < 3 && talon.size() > 0; i++)
        cards.add(talon.remove(0));
    // This should register as a move with 0 cards being moved
    fireStackReceivedCards(0);
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
    return false;
  }
}