package woo.notifications;

import woo.notifications.delivery.NotificationsDelivery;
import woo.products.Product;

import java.io.Serializable;

/**
 * Represents a notification used when the price of a product lowers.
 */
public class Bargain extends Notifications implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Bargain class constructor
     *
     * @param product product
     * @param delivery notification delivery method
     */
    public Bargain (Product product, NotificationsDelivery delivery){
        super(product, delivery);
    }


    /**
     * Gets a string representation of the bargain.
     *
     * @return return a visual representation of the bargain as a string
     */
    @Override
    public String toString(){
        return "BARGAIN|" + this.getProduct().getId() + "|" + this.getProduct().getPrice();
    }
}
