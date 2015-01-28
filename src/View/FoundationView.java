package Solitaire.View;
import Solitaire.Model.*;

import java.awt.Graphics;
import java.util.List;

/* Provides the View/Controller for a Foundation stack. */

public class FoundationView extends StackView {

  // The model
  private Foundation model;
  protected Stack model(){ return model; }

  public FoundationView(Foundation m, TableView t, int x, int y){
    super(t,x,y, TableView.CardWidth, TableView.CardHeight);
    model = m;
  }

  @Override
  // Post: g shows this stack at postion (x,y)
  public void draw(Graphics g){

    // If there is no card that's not being dragged, draw outline
    if (model.visible() - dragging < 1)
      Images.outline(x,y,g);
    // Otherwise, draw top card
    else 
      Images.front(model.top(1+dragging).get(0),x,y,g);
  }

  @Override
  // Post: returns how many cards would physically be dragged at (px,py)
  public int startDrag(int px, int py){
    table.setOffset(px,py);
    return 1;
  }
}