package woo.exceptions;

/**
 * Exception thrown when we don't have a supplier corresponding to this key. Only used to catch error.
 * */
public class InvalidTransactionKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Transaction's unique key */
  private Integer key;


  /**
   * InvalidTransactionKeyException class constructor
   *
   * @param key transaction's key
   */
  public InvalidTransactionKeyException (Integer key){
    this.key = key;
  }


  /**
   * Gets transaction's key
   *
   * @return transaction's key as a Integer
   */
  public Integer getKey(){ return this.key;}

}