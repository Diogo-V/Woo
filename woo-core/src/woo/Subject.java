package woo;

/**
 * Represents a subject - it's the one being observed and sends notification to the observer.
 */
public interface Subject {
  /* Register a new observer */
  public void registerObserver(String key, Observer o);


  /* Removes the observer */
  public void removeObserver(String key);


  /* Notify all observers */
  public void notifyObservers(String type);
}
