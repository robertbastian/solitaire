package Solitaire.View;
import Solitaire.Model.*;

import java.awt.Graphics;

/* This provides the View/Controller for a Talon stack. Since cards can't be moved onto the Talon, it is only a SourceView */

public class TalonView extends StackView {

  // The model
  private Talon model;
  protected Stack model(){ return model; }

  public TalonView(Talon m, TableView t, int x, int y){
    super(t,x,y, 2*(TableView.CardWidth+TableView.CardShift)+TableView.Margin,TableView.CardHeight);
    model = m;
  }

  @Override
  // Post: g shows this stack at postion (x,y)
  public void draw(Graphics g){

    // Talon, either the outline or just a card back
    if (model.talonEmpty())
      Images.outline(x,y,g);
    else
      Images.back(x,y,g);

    // Waste, top cards (3 if available), one less if top card is being dragged
    for (int i = 0; i < model.visible() - dragging; i++)
      Images.front(model.top(model.visible()).get(i), x + TableView.Margin + TableView.CardWidth + i * TableView.CardShift, y,g);
  }

  @Override
  // Post: returns how many cards would physically be dragged at (px,py)
  public int startDrag(int px, int py){
    // Did we miss the waste?
    if (px < TableView.CardWidth+TableView.Margin)
      return 0;

    // Dragging anywhere on the waste will just result in the top card being dragged
    table.setOffset(px - TableView.CardWidth - TableView.Margin - TableView.CardShift * Math.min(2,model.size()-1), py);
    return 1;
  }

  @Override
  public void handleClick(int px, int py){
    // If the talon is clicked, the reveal() method is called
    if (px < TableView.CardWidth)
      model.reveal();
  }
}