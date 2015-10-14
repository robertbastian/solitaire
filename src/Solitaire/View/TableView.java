package Solitaire.View;
import Solitaire.Model.*;

import java.util.List;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/* Provides the visual of a table, controls mouse events and controls moving cards */

public class TableView extends JPanel implements MouseListener, MouseMotionListener {

  static final int TableWidth = 640, TableHeight = 500,
                   TalonX = 20, TalonY = 20, 
                   FoundationX = 269, FoundationY = 20, 
                   TableauX = 20, TableauY = 160,
                   CardWidth = 73, CardHeight = 97,
                   Margin = 10, CardShift = 20;

  // Foundations, Tableaus and Talon
  private StackView[] stackViews;

  // What cards are currently being dragged ...
  private List<Card> movingStack;
  // ... where they come from ...
  private StackView source;
  // ... and where they are in relation to the table and the mouse.
  private int dragPointX, dragPointY, dragOffsetX, dragOffsetY;

  public TableView(Game g){
    setLayout(null);
    setPreferredSize(new java.awt.Dimension(TableWidth, TableHeight));

    addMouseListener(this);
    addMouseMotionListener(this);

    setGame(g);
  }

  // Initializes views for this game
  public void setGame(Game game){
    stackViews = new StackView[12];
    for (int i = 0; i < 7; i++)
      stackViews[i] = new TableauView(game.tableau(i),this, TableauX + i * (Margin+CardWidth), TableauY);
    for (int i = 0; i < 4; i++)
      stackViews[i+7] = new FoundationView(game.foundation(i),this, FoundationX + i * (Margin+CardWidth), FoundationY);
    stackViews[11] = new TalonView(game.talon(),this, TalonX, TalonY);
  }

  @Override
  public void paintComponent(Graphics g) {
    // Draw background
    Images.background(0,0,g);

    // Draw all stacks
    for (int i = 0; i < stackViews.length; i++)
      stackViews[i].draw(g);

    // Draw the dragged stack
    if (isDragging())
      for (int i = 0; i < movingStack.size(); i++)
        Images.front(movingStack.get(i),dragPointX, dragPointY + i*CardShift,g);
  }

  /* Dragging logic */
  public void startDrag(List<Card> cs, StackView src){
    movingStack = cs;
    source = src;
  }

  public void setOffset(int px, int py){
    dragOffsetX = px; dragOffsetY = py;
  }

  public boolean isDragging(){
    return movingStack != null;
  }

  public List<Card> movingStack(){
    return movingStack;
  }

  public void endDrag(boolean success){
    assert isDragging();
    source.endDrag(success);
    movingStack = null; source = null;
    repaint();
  }

  /* Mouse Events */
  // Every StackView will be notified, and because they are disjoint, at most one will execute something.
  @Override
  public void mousePressed(MouseEvent e){
    // Try all StackViews and stop once one responded
    for (int i = 0; i < 12 && !stackViews[i].pressed(e.getX(),e.getY()); i++);
    // mouseDragged is not fired before moving the cursor, so we have to do so manually
    mouseDragged(e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // No need to do this if we're not actually moving anything
    if (isDragging()){
      dragPointX = e.getX() - dragOffsetX; 
      dragPointY = e.getY() - dragOffsetY;
      repaint();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e){
    for (int i = 0; i < 12 && !stackViews[i].clicked(e.getX(),e.getY()); i++);
  }

  @Override
  public void mouseReleased(MouseEvent e){
    if (isDragging()){
      for (int i = 0; i < 12 && !stackViews[i].released(e.getX(),e.getY()); i++);
      // If user didn't let go above a stack, end drag manually
      if (isDragging())
        endDrag(false);
    }
  }

  // If the mouse exits the window and we are dragging, abort
  @Override
  public void mouseExited(MouseEvent e){
    if (isDragging())
      endDrag(false);
  }

  @Override
  public void mouseMoved(MouseEvent e){}
  @Override
  public void mouseEntered(MouseEvent e) {}
}