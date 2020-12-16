package woo.notifications;

import woo.notifications.delivery.NotificationsDelivery;
import woo.products.Product;

import java.io.Serializable;

/**
 * Represents a notification used when the stock of a product pass from 0 to a positive number.
 */
public class New extends Notifications implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /**
     * New class constructor
     *
     * @param product product
     * @param delivery notification delivery method
     */
    public New (Product product, NotificationsDelivery delivery){
        super(product, delivery);
    }


    /**
     * Gets a string representation of the new.
     *
     * @return return a visual representation of the new as a string
     */
    @Override
    public String toString(){
        return "NEW|" + this.getProduct().getId() + "|" + this.getProduct().getStock();
    }
}
