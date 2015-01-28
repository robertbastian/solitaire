package Solitaire.View;
import Solitaire.Model.*;

import java.awt.Graphics;

/* This provides the visual skeleton for a stack, including drawing and mouse events */

public abstract class StackView {

	protected int x,y,width,height, dragging;
	protected TableView table;
	protected abstract Stack model();

	public StackView(TableView t, int x, int y, int w, int h){
		table = t;
		this.x = x; this.y = y;
		width = w; height = h;
	}

	// Post: g shows this stack at postion (x,y)
	public abstract void draw(Graphics g);

	// Post: returns how many cards would physically be dragged at (px,py)(relative to the stack)
	public abstract int startDrag(int px, int py);

	// Finishes a move and conceptually removes cards from this stack if move successful
	public void endDrag(boolean success){
		if (success)
			model().removeCards(dragging);
		dragging = 0;
	}

	/* Responding to mouse events. The px and py coordinates are relative to this stack */
		
	// When this stack is pressed
	protected void handlePress(int px, int py){
		// How many cards are physically being dragged?
		int n = startDrag(px,py);
		// Are we dragging any?
		if (n == 0)
			return;
		// Are we allowed to drag this many?
		if (!model().validRemove(n))
			return;
		// Start the drag with top n cards
		table.startDrag(model().top(n), this);
		dragging = n;
	}	

	// Pre: table.isDragging();
	protected void handleRelease(int px, int py){
		assert table.isDragging();
		// End the drag with result of adding cards to this stack as the success value
		table.endDrag(model().addCards(table.movingStack()));
	}

	protected void handleClick(int px, int py){}
	
	/* These calls from TableView correspond to the MouseListener events. If they are within this stack, the associated handle-method will be called and the method returns true */
	public boolean pressed(int px, int py){
		if (!inside(px,py))
			return false;
		handlePress(px-x,py-y);
		return true;
	}

	public boolean clicked(int px, int py){
		if (!inside(px,py))
			return false;
		handleClick(px-x,py-y);
		return true;
	}

	public boolean released(int px, int py){
		if (!inside(px,py))
			return false;
		handleRelease(px-x,py-y);
		return true;
	}

	private boolean inside(int px, int py){
		return (px >= x && px <= x+width && py >= y && py <= y + height);
	}
}