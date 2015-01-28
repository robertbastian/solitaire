package Solitaire.View;
import Solitaire.Model.*;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.File;

/* This class statically loads all the images and provides static methods to draw them. */

public final class Images {
    
  private static final String directory = "resources/", extension = ".gif", cardBackFilename = "back", backgroundName = "felt.jpg";
  private static HashMap<Card,Image> fronts = new HashMap<Card,Image>();
  private static Image back;
  private static Image background;

  static {
    try {
      for (Card c : Card.fullDeck())
        fronts.put(c,ImageIO.read(new File(directory + name(c) + extension)));
      // Random back image everytime the game is run!
      back = ImageIO.read(new File(directory + cardBackFilename + extension));
      background = ImageIO.read(new File(directory + backgroundName)); 
    }
    catch (Exception e){ e.printStackTrace(); }
  }

  private static String name(Card c){
    char suit = ' ';
    switch (c.suit){
      case Clubs: suit = 'c'; break;
      case Diamonds: suit = 'd'; break;
      case Hearts: suit = 'h'; break;
      case Spades: suit = 's'; break;
    }
    return ((c.number < 10) ? "0" : "") + c.number + suit;
  }

  // Draws the picture of c onto g at x,y
  public static void front(Card c, int x, int y, Graphics g){
    g.drawImage(fronts.get(c),x,y,null);
  }

  // Draws a card back onto g at x,y
  public static void back(int x, int y, Graphics g){
    g.drawImage(back,x,y,null);
  }

  // Draws a card outline onto g at x,y
  public static void outline(int x, int y, Graphics g){
    ((Graphics2D) g).draw(new RoundRectangle2D.Float(x, y,TableView.CardWidth,TableView.CardHeight,10,10));
  }

  // Draws the background image onto g at x,y
  public static void background(int x, int y, Graphics g){
    g.drawImage(background,x,y,TableView.TableWidth,TableView.TableHeight,null);
  }
}