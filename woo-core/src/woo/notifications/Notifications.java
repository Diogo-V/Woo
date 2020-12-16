package woo.notifications;

import woo.notifications.delivery.*;
import woo.products.Product;

import java.io.Serializable;


public class Notifications implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Holds the product */
    private Product product;

    /** Holds the notification delivery method */
    private NotificationsDelivery delivery;


    /**
     * Notification class constructor
     *
     * @param product product
     * @param delivery notification delivery method
     */
    public Notifications(Product product, NotificationsDelivery delivery) {
        this.product = product;
        this.delivery = delivery;
    }


    /**
     * Gets product
     *
     * @return product
     */
    public Product getProduct(){
        return this.product;
    }


    /**
     * Gets notification delivery method
     *
     * @return delivery as a NotificationDelivery
     */
    public NotificationsDelivery getDelivery(){
        return this.delivery;
    }


}
