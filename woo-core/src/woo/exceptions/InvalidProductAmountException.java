package woo.exceptions;


/**
 * Exception thrown when it is requested an unavailable quantity of a product.
 * */
public class InvalidProductAmountException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Requested amount */
    private int requested;

    /** Available amount */
    private int available;


    /**
     * InvalidProductAmountException class constructor
     *
     * @param requested amount requested
     * @param available amount available
     * */
    public InvalidProductAmountException(int requested, int available) {
        this.requested = requested;
        this.available = available;
    }


    /**
     * Gets requested amount.
     *
     * @return requested amount
     * */
    public int getRequested() { return requested; }


    /**
     * Gets available amount.
     *
      @return available amount
     * */
    public int getAvailable() { return available; }

}
