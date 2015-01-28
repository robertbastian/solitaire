package Solitaire.Model;

/* Provides interface to listen to a Stack's events */

public interface StackListener {
  public void stackReceivedCards(Stack s, int n);
}