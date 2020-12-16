package woo;

import woo.notifications.Notifications;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an observer - it's the one who observes the subject and receives notifications.
 */
public abstract class Observer implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** History of all sent notifications */
  private ArrayList<Notifications> receivedNotifications = new ArrayList<>();


  /**
   * Gets all the sent notifications.
   * */
  public ArrayList<Notifications> getReceivedNotifications(){
    return this.receivedNotifications;
  }


  /**
   * Gets all the sent notifications sorted by sent order. Also clears notifications history before returning.
   * */
  public String sendReceivedNotifications() {
    StringBuilder result = new StringBuilder();
    this.receivedNotifications.forEach(notifications -> result.append(notifications.toString()).append("\n"));
    this.receivedNotifications.clear();
    return result.toString();
  }
}
