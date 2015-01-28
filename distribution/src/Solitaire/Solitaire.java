/** Initial version of Solitaire.java
 */

package Solitaire;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Solitaire extends JApplet {
	public static final int suitSize = 13;
	public static final int baseX = 40, baseY = 10, shiftX = 80, shiftY = 15;
	public static final int TableWidth = 640, TableHeight = 500; 
		// default table window size
	static protected JFrame outerFrame; // the whole window with its frame
	static protected Solitaire innerFrame; // the inside of the frame
    static protected JPanel buttonPanel;
    static protected CardTable table;

    public static void main(String[] args) {
        
        outerFrame = new JFrame( "Solitaire");            
		Solitaire innerFrame = new Solitaire(); 
		buttonPanel = new JPanel(); 
		buttonPanel.setLayout(new FlowLayout());
		innerFrame.setLayout(new FlowLayout());

    	JButton bs = new JButton("Shuffle");
		bs.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e) {
			// Shuffle the cards
			Pack.Shuffle( table.getPack());
			table.clearMarkers();
			table.repaint();
		    }
		});
		// And add the button to the panel
		buttonPanel.add(bs);
		
		// Create a quit button
		JButton bq = new JButton("Quit");
		bq.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e) {
		    	System.exit(0);
		    }
		});
		buttonPanel.add(bq);	

		table = new CardTable();		
		table.setPreferredSize(new Dimension( TableWidth, TableHeight));
		innerFrame.add( table);
		innerFrame.add( buttonPanel);

		outerFrame.add(innerFrame);
		outerFrame.pack();
        outerFrame.setVisible(true);
    }
}

