package woo.transactions;

import woo.suppliers.Supplier;
import java.io.Serializable;


/**
 * Represents a transaction between a Supplier and the Store. Holds multiple products in multiple quantities.
 * */
public class Order extends Transaction implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Supplier that is fulfilling this order */
    private Supplier supplier;


    /**
     * Order class constructor.
     *
     * @param id transaction identifier
     * @param supplier product's supplier
     * */
    public Order(int id, Supplier supplier) {
        super(id);
        this.supplier = supplier;
    }


    /**
     * Gets associated supplier.
     *
     * @return supplier
     * */
    public Supplier getSupplier() { return this.supplier; }


    /**
     * Finishes order processing by storing date of payment and total price paid.
     *
     * @param date date of payment
     * */
    public void finish(int date) { setDateOfPayment(date); setBasePrice(getTotalPrice()); }


    @Override
    public String toString() {

        /* Holds string that is going to be displayed */
        StringBuilder result = new StringBuilder();

        /* Appends tittle */
        result.append(getId()).append("|").append(getSupplier().getId()).append("|").append(getBasePrice()).append("|").
                append(getDateOfPayment()).append("\n");

        /* Iterates over each link in cart, creates a string and adds to our result */
        for (ProductAmountLink link : getCart()) {
            result.append(link.getProduct().getId()).append("|").append(link.getAmount()).append("\n");
        }

        return result.toString();
    }

}
