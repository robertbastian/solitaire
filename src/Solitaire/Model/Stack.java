package Solitaire.Model;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/* Models a Stack of cards */

public abstract class Stack extends EventBroadcaster<StackListener> {

  /* 
    Abs: (1) Stack = cards 
         (2) 0 <= i < j < cards.size => cards[i] is conceptually below cards[j]
    DTI: (1) cards != null 
  */
  
  protected List<Card> cards;

  // Post: cards = []
  public Stack(){ 
    cards = new ArrayList<Card>();
  }

  // Post: returns (length cards)
  public int size(){
    return cards.size();
  }

  // Post: returns the number of visible cards
  public abstract int visible();

  // Pre: n <= visible()
  // Post: returns top n visible cards
  public List<Card> top(int n){
    assert n <= visible();
    return cards.subList(size()-n,size());
  }

  // Pre: n > 0
  // Post: returns whether removing n cards from this stack is a valid move according to the rules of the game
  public abstract boolean validRemove(int n);

  // Pre: n > 0 && validRemove(n)
  // Post: if validRemove(n): cards = cards[0..size-n)
  public void removeCards(int n) {
    assert n > 0 && validRemove(n);
    for (int i = 0; i < n; i++)
      cards.remove(size()-1);
  }

  // Pre: cs != null && cs != []
  // Post: returns whether adding cs to cards is a valid move according to the rules of the game
  public abstract boolean validAdd(List<Card> cs);
  
  // Pre: cs != null && cs != []
  // Post: returns validAdd(cs), if validAdd(cs): cards = cards0 ++ cs
  public boolean addCards(List<Card> cs) {
    if (!validAdd(cs))
      return false;
    cards.addAll(cs);
    fireStackReceivedCards(cs.size());
    return true;
  }

  protected void fireStackReceivedCards(int n){
    for (StackListener l : listeners)
      l.stackReceivedCards(this,n);
  }
}