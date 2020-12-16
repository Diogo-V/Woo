package woo.exceptions;

/**
 * Exception thrown when we don't have a supplier corresponding to this key. Only used to catch error.
 * */
public class InvalidSupplierKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Supplier's unique key */
  private String key;


  /**
   * InvalidSupplierKeyException class constructor
   *
   * @param key supplier's key
   */
  public InvalidSupplierKeyException (String key){
    this.key = key;
  }


  /**
   * Get supplier's key
   *
   * @return supplier's key as a String
   */
  public String getKey(){ return this.key;}
}
