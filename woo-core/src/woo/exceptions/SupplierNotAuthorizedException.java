package woo.exceptions;

/**
 * Exception thrown when this supplier is not authorized to perform any transaction with the store.
 * */
public class SupplierNotAuthorizedException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Supplier's key */
    private String supplierKey;


    /**
     * ProductNotSuppliedException class constructor.
     *
     * @param supplierKey supplier's identifier
     * */
    public SupplierNotAuthorizedException(String supplierKey) {
        this.supplierKey = supplierKey;
    }


    /**
     * Gets supplier's key.
     *
     @return supplier's key
      * */
    public String getSupplierKey() { return supplierKey; }

}