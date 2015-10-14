package Solitaire.Model;

import java.util.HashSet;

/* 'Mixin' providing a set of event handlers and methods to add and remove from it */

public class EventBroadcaster<EventHandler> {

  /* DTI: listeners != null */

  protected HashSet<EventHandler> listeners;

  // Post: listeners = {}
  public EventBroadcaster(){
      listeners = new HashSet<EventHandler>();
  }

  // Post: listeners = listeners0 U {l}
  public void addListener(EventHandler l){ 
    if (l != null) listeners.add(l); 
  }

  // Post: listener = listeners0 \ {l}
  public void removeListener(EventHandler l){
    if (l != null) listeners.remove(l);
  }
}