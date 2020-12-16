package woo.exceptions;


/**
 * Exception thrown when product is not supplied by input supplier.
 * */
public class ProductNotSuppliedException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Product's key */
    private String productKey;

    /** Supplier's key */
    private String supplierKey;


    /**
     * ProductNotSuppliedException class constructor.
     *
     * @param productKey product's identifier
     * @param supplierKey supplier's identifier
     * */
    public ProductNotSuppliedException(String productKey, String supplierKey) {
        this.productKey = productKey;
        this.supplierKey = supplierKey;
    }


    /**
     * Gets product's key.
     *
     * @return product's key
     * */
    public String getProductKey() { return productKey; }


    /**
     * Gets supplier's key.
     *
      @return supplier's key
     * */
    public String getSupplierKey() { return supplierKey; }

}
