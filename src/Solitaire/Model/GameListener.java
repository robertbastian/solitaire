package Solitaire.Model;

/* Provides interface to listen to a State's events */

public interface GameListener {
	public void stateChanged(int moves, int cardsMoved);
	public void gameOver(int moves);
}