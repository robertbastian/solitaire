package Solitaire.Model;

import java.util.List;
import java.util.HashSet;

/* Models an entire game of Solitaire */

public class Game extends EventBroadcaster<GameListener> implements StackListener {

  /* Abs: trivial
     DTI: (1) 0 <= i < 4 => foundations[i] != null
          (2) 0 <= i < 7 => tableaus[i] 
          (3) talon != null
          (4) 0 <= moves
          (5) 0 <= cardsMoved */

  private Foundation[] foundations;
  private Tableau[] tableaus;
  private Talon talon;
  private int moves,cardsMoved;

  public Game(GameListener l){
    
    // Shuffling the cards
    List<Card> deck = Card.shuffledDeck();

    // Creating the 4 empty foundations
    foundations = new Foundation[4];
    for (int i = 0; i < 4; i++){
      foundations[i] = new Foundation();
      foundations[i].addListener(this);
    }

    // Creating the talon with the first 24 cards (the cards are shuffled, so the dealing order does not matter)
    talon = new Talon(deck.subList(0,24));
    talon.addListener(this);

    // Creating the 7 tableaus with the rest of the cards 
    tableaus = new Tableau[7];
    for (int i = 0, offset = 24; i < 7; offset += ++i){
      tableaus[i] = new Tableau(deck.subList(offset,offset+i+1));
      tableaus[i].addListener(this);
    }

    addListener(l);
  }
  
  // Pre: 0 <= i < 4
  // Post: returns the ith foundation
  public Foundation foundation(int i){
    assert i >= 0 && i < 4;
    return foundations[i];
  } 

  // Post 0 <= i < 7
  // Post: returns the ith tableau
  public Tableau tableau(int i){
    assert i >= 0 && i < 7;
    return tableaus[i];
  }

  // Post: returns the talon/waste
  public Talon talon(){
    return talon;
  }

  @Override
  // Pre: n >= 0
  // Post: moves = moves0 + 1; cardsMoved = cardsMoved0 + n
  public void stackReceivedCards(Stack s, int n){
    assert n >= 0;
    moves++; 
    cardsMoved += n;
    fireStateChanged();
    // If the event source is a foundation we might have won
    if (s instanceof Foundation && finished()) 
      fireGameOver();
  }

  // Post: returns whether the game is over
  private boolean finished(){
    for (int i = 0; i < 4; i++)
      if (foundations[i].size() < Card.suitSize)
        return false;
    return true;
  }

  protected void fireStateChanged(){
    for (GameListener l : listeners)
      l.stateChanged(moves,cardsMoved);
  }

  protected void fireGameOver(){
    for (GameListener l : listeners)
      l.gameOver(moves);    
  }
}