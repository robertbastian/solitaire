package Solitaire.Model;

import java.util.List;
import java.util.ArrayList;

/* Models a Tableau stack */

public class Tableau extends Stack {

  /* 
    Additons to Stack's specification: 
    Abs: cards[0..hidden) are upside down
    DTI: (1) size > 0 => hidden < size 
         (2) size > 0 => cards[hidden..size) satisfy Tableau requirement
         (3) size > 0 && hidden = 0 => cards[0].number = 13 
         (4) size = 0 => hidden = 0

    A sequence of cards xs satisfies the Tableau requirement iff: 
      0 <= i < size -1 => xs[i].number = xs[i+1].number + 1 && xs[i].color != xs[i+1].color
      (i.e alternating colors and ascending values) 
  */

  private int hidden;

  // Pre: cs != null
  // Post: cards = cs && hidden = size() - 1;
  public Tableau(List<Card> cs){
    assert cs != null;
    cards.addAll(cs);
    // At beginning of game, only top card is visible
    hidden = size()-1;
  }

  @Override
  // Post: returns the number of visible cards
  public int visible(){
    return size()-hidden;
  }

  @Override
  // Post: returns whether removing n cards from this stack is a valid move according to the rules of the game
  public boolean validRemove(int n){
    // Checking DTI.1
    return hidden <= size()-n;
  }

  @Override
  // Pre: n > 0
  // Post: if validRemove(n): cards = cards[0..size-n)
  public void removeCards(int n){
    super.removeCards(n);
    // If top card is hidden, reveal it (maintaining DTI.1 and DTI.4)
    if (size() > 0 && hidden >= size())
      hidden = size() - 1;
  }

  @Override
  // Pre: cs != null && cs != []
  // Post: returns whether adding cs to cards is a valid move according to the rules of the game
  public boolean validAdd(List<Card> cs){
    assert cs != null && cs.size() > 0;
    Card c = cs.get(0);

    // Checking DTI.3: only a king may be added to an empty tableau
    if (size() == 0)
      return c.number == 13;

    // Checking DTI.2: new cards have to be a tableau and compatible
    return isTableau(cs) && compatible(cards.get(size()-1),c);
  }

  // Pre: bottom != null && top != null
  // Post: returns whether [bottom,top] satisfies Tableau requirement
  private boolean compatible(Card bottom, Card top){
    assert bottom != null && top != null;
    return (bottom.number == top.number + 1 && bottom.color ^ top.color);
  }

  // Pre: cs != null && cs != []
  // Post: returns whether cs satisifies Tableau requirement
  private boolean isTableau(List<Card> cs){
    assert cs != null && cs.size() > 0;
    // I: cs[0..i) satisfies Tableau requirement
    for (int i = 1; i < cs.size(); i++){
      if (!compatible(cs.get(i-1),cs.get(i)))
        return false;
    }
    return true;
  }
}