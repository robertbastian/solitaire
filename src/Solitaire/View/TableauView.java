package Solitaire.View;
import Solitaire.Model.*;

import java.awt.Graphics;

/* This provides the View/Controller for a Tableau stack. Since cards can be both moved from and onto a Foundation, it is a TargetView (and by extension a SourceView). */

public class TableauView extends StackView {

  // The model
  private Tableau model;
  protected Stack model(){ return model; }

  public TableauView(Tableau m, TableView t, int x, int y){
    super(t,x,y,TableView.CardWidth,TableView.TableHeight - y);
    model = m;
  }

  @Override
  // Post: g shows this stack at postion (x,y)
  public void draw(Graphics g){

    // Outline
    if (model.size() - dragging == 0)
      Images.outline(x,y,g);
      
    // Hidden cards
    int hidden = model.size() - model.visible();
    for (int i = 0; i < hidden; i++)
      Images.back(x, y + i * TableView.CardShift,g);

    // Open cards (except the top cards if they are being dragged)
    for (int i = 0; i < model.visible() - dragging; i++)
      Images.front(model.top(model.visible()).get(i),x,y + (i+hidden) * TableView.CardShift,g);
  }

  @Override
  // Post: returns how many cards would physically be dragged at (px,py)
  public int startDrag(int px, int py){

    // Did we miss the stack?
    if (py > TableView.CardHeight + (model.size()-1) * TableView.CardShift)
      return 0;

    // Did we hit the top card?
    if (py > (model.size()-1) * TableView.CardShift){
      table.setOffset(px,py - (model.size()-1) * TableView.CardShift);
      return 1;
    }

    // How many cards aren't being dragged?
    int n = py / TableView.CardShift;

    table.setOffset(px,py - n * TableView.CardShift);
    return model.size() - n;
  }
}