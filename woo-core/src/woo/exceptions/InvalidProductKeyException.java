package woo.exceptions;

/**
 * Exception thrown when we don't have a product corresponding to this key. Only used to catch error.
 * */
public class InvalidProductKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Product's unique key */
  private String key;


  /**
   * InvalidProductKeyException class constructor
   *
   * @param key product's key
   */
  public InvalidProductKeyException (String key){
    this.key = key;
  }


  /**
   * Gets product's key
   *
   * @return product's id as a String
   */
  public String getKey(){ return this.key;}
}


