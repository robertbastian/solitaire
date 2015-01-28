package Solitaire;

/** A subclass of JPanel that incorporates the visual idea of a card table, and a pack of cards upon it
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


@SuppressWarnings("serial")
class CardTable extends JPanel {
    protected static final int Margin=40, XShift=80, YShift=160, CardShift=15;
	static List<Card> pack = new ArrayList<Card>( 52);
    static int anchorX, anchorY, currentX, currentY;
    static boolean drawLine = false;
    static boolean lineRed = false;
	
    public void clearMarkers() { drawLine = false; }
    
	public void paintComponent(Graphics g) {
		super.paintComponent( g);
    	g.setColor( Color.green);
    	g.fillRect( 0, 0, getWidth(), getHeight());
    	Card back = Card.getCardBack();	    	
    	Card outline = Card.getCardOutline();
    	
    	for ( int coln=0 ; coln<7 ; coln++ ) {
    		if ( coln!=2 )  outline.show( g, this, Margin+coln*XShift, Margin);
    		outline.show( g, this, Margin+coln*XShift, Margin+YShift);
    	}
    	// An odd card back, just to show one
    	back.show(g, this, Margin, Margin);
	    for ( int cn=0 ; cn<52 ; cn++ ) {
		  pack.get( cn).show( g, this, Margin+(cn/13)*XShift, Margin+YShift+CardShift*(cn%13));
	      }
	    
	    if ( drawLine ){
	    	g.setColor( lineRed ? Color.RED : Color.BLACK);
	    	g.drawLine( anchorX,  anchorY,  currentX,  currentY);
	    }

	}
	
	public CardTable() {
		
		for ( Suit s: Suit.values() ) {
		    for ( int cn=0 ; cn<13 ; cn++ ) {
				pack.add( Card.getCard( s, cn+1));
				// cards are indexed in the range [1..Solitaire.suitSize]
		    }
		}
		
    	// Define, instantiate and register a MouseListener object
    	addMouseListener(new MouseAdapter() {
    	    public void mousePressed(MouseEvent e) {
    		// remember the end of a rubber line
    		anchorX = e.getX();
    		anchorY = e.getY();
    	    }
    	});

    	// Define, instantiate and register a MouseListener object
    	addMouseListener(new MouseAdapter() {
    	    public void mouseReleased(MouseEvent e) {
    		// paint the last rubber-line in red
    		currentX = e.getX();  currentY = e.getY();
    		drawLine = true; lineRed = true;
    		repaint();
    	    }
    	});

    	// Define, instantiate and register a MouseMotionListener object
    	addMouseMotionListener(new MouseMotionAdapter() {
    	    public void mouseDragged(MouseEvent e) {
    		// draw a rubber line (in black)
    		currentX = e.getX();  currentY = e.getY();
    		drawLine = true; lineRed = false; 	
    		repaint();
    	    }
    	});
    	
		repaint();
	}

    public List<Card> getPack() { return pack; }
    
    public Dimension getMinimumSize() {
    	return getPreferredSize();
    }

    public Dimension getMaximumSize() {
    	return getPreferredSize();
    }
    
}