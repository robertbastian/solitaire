package Solitaire;

/** A class for a single playing card
 * 
 */
import java.awt.*;

import javax.swing.JComponent;

public class Card {
	// the directory where the card images are to be found
    private final static String directory = "cards", extension = ".gif",
    		cardBackFilename = "back191", cardOutlineFilename = "bottom01";

    private Image im;  // its picture
    
    // Grab a standard card.  s is the suit; card is the card number in the range 1-13 
    public static Card getCard( Suit s, int card) { return new Card( directory+CardFile( s, card)); }
    
    // Grab a card back
    public static Card getCardBack() { return new Card( directory+"/"+cardBackFilename+extension); }

    // Grab a card outline
    public static Card getCardOutline() { return new Card( directory+"/"+cardOutlineFilename+extension); }

    private Card( String name) {
	try {
		im = Toolkit.getDefaultToolkit().getImage( name);
		}
	catch ( Exception e ) {
	    System.err.println( "Error " + e.getMessage() );
		}
    }

    // Draw the picture of a card onto a graphics context g, positioned at (x,y)
    public void show( Graphics g, JComponent c, int x, int y) {
    	g.drawImage( im, x, y, c);
    }

    protected static String CardFile( Suit s, int card) throws IllegalArgumentException {
		char sc;
			
		if ( card < 1 || card > Solitaire.suitSize )
		    throw new IllegalArgumentException( "Bad Card Number");
	
		if ( s==Suit.Clubs )
		    sc = 'c';
		else if ( s==Suit.Diamonds )
		    sc = 'd';
		else if ( s==Suit.Hearts )
		    sc = 'h';
		else if ( s==Suit.Spades )
		    sc = 's';
		else
		    throw new IllegalArgumentException( "Bad Card Suit");
	
		if ( card<10 )
		    return "/0" + card + sc + extension;
		else
		    return "/" + card + sc + extension;
	    }
}

