package woo.exceptions;

/**
 * Exception thrown when we don't have a client corresponding to this key. Only used to catch error.
 * */
public class InvalidClientKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Client's unique key */
  private String key;


  /**
   * InvalidClientKeyException class constructor
   *
   * @param key client's key
   */
  public InvalidClientKeyException (String key){
    this.key = key;
  }


  /**
   * Get client's key
   *
   * @return client's key as a String
   */
  public String getKey(){ return this.key;}
}
