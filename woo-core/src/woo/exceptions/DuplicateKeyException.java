package woo.exceptions;

/**
 * Exception thrown when we have a duplicate key. Only used to catch error.
 * */
public class DuplicateKeyException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Element's unique key */
    private String key;


    /**
     * Element class constructor
     *
     * @param key element's key
     */
    public DuplicateKeyException (String key){
        this.key = key;
    }


    /**
     * Gets element's key
     *
     * @return element's key as a String
     */
    public String getKey(){ return this.key;}
}
