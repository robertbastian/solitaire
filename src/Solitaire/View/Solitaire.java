package Solitaire.View;
import Solitaire.Model.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/* This is the program's main Frame. It contains the TableView as well as labels with statistics (such as the number of moves) and a button to start a new game. */

public class Solitaire extends JFrame implements GameListener, ActionListener {

  private TableView table;
  private JLabel movesLabel, cardMovedLabel;

  public static void main(String[] args){
    new Solitaire();
  }

  public Solitaire(){
    super("Solitaire");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new BorderLayout());
    
    JPanel south = new JPanel();
    south.setLayout(new GridLayout(1,3));
    add(south, BorderLayout.SOUTH);

    movesLabel = new JLabel();
    movesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    south.add(movesLabel);

    cardMovedLabel = new JLabel();
    cardMovedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    south.add(cardMovedLabel);

    JButton reset = new JButton("New Game");
    reset.addActionListener(this);
    south.add(reset);

    table = new TableView(new Game(this));
    add(table, BorderLayout.CENTER);

    pack();
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e){
    newGame();
  }

  private void newGame(){
    table.setGame(new Game(this));
    stateChanged(0,0);    
  }

  @Override
  public void stateChanged(int moves, int cardsMoved){
    movesLabel.setText("Moves: "+moves);
    cardMovedLabel.setText("Cards moved: "+cardsMoved);
    repaint();
  }

  @Override
  public void gameOver(int moves){
    Object[] options = {"Yes, it's so fun!","Quit"};
    if (0 == JOptionPane.showOptionDialog(this, "You won in "+moves+" moves!\nPlay another game?", "Congratulations", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]))
      newGame();
    else
      System.exit(0);
  }
}