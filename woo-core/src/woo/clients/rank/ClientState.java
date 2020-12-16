package woo.clients.rank;

import woo.clients.Client;
import java.io.Serializable;


/**
 * Represents a client's state. Hold's the client and the client's points.
 */
public abstract class ClientState implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Associated client */
    private Client client;

    /** Accumulated points (used to manage client's rank) */
    private int points;


    /**
     * ClientState class constructor.
     *
     * @param client client
     * @param points client's points
     */
    public ClientState(Client client, int points) {
        this.client = client;
        this.points = points;
    }


    /**
     * Gets client associated with this state.
     *
     * @return client
     * */
    public Client getClient() { return this.client; }


    /**
     * Gets client's accumulated points.
     *
     * @return number of points
     * */
    public int getPoints() { return this.points; }


    /**
     * Paid before hitting the product's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    abstract int calculatesFinalPriceBeforeProductDeadline(int period, int price);


    /**
     * Paid before hitting the sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    abstract int calculatesFinalPriceBeforeSaleDeadline(int period, int price);


    /**
     * Paid before product's deadline after sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    abstract int calculatesFinalPriceAfterSaleDeadline(int period, int price);


    /**
     * Paid after product's deadline after sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    abstract int calculatesFinalPriceAfterProductDeadline(int period, int price);


    /**
     * Checks, based on current accumulated points, if we should change our status to one of the others.
     *
     * @param period Period of time elapsed before paying. If negative, means that this person has passed the
     *               time deadline and if positive, it was paid before it
     * */
    abstract void updateStatus(int period);


    /**
     * Applies changes to this client's number of points.
     *
     * @param points points variation to be credited to this client
     */
    private void updatePoints(int points) { this.points += points; }


    /**
     * Gets final price after all calculations, updates number of points and client rank if needed.
     *
     * @param period Period of time elapsed before paying. If negative, means that this person has passed the
     *               time deadline and if positive, it was paid before it
     * @param deadline Product's deadline date
     * @param price Product's price per unit
     *
     * @return total paid by the client
     */
    public int processPayment(int period, int deadline, int price) {

        /* Gets price that is going to be paid after discounts and penalties */
        int finalPrice = this.getsFinalPrice(period, deadline, price);

        /* If sale was paid on time, we increase our number of points */
        if (period >= 0) updatePoints(finalPrice * 10);

        /* Now, we update client status based on elapsed time before paying */
        updateStatus(period);

        return finalPrice;
    }


    /**
     * Gets final price after all calculations (discounts and penalties). Decides which method of calculation to use
     * depending on the passed period of time.
     * 
     * @param period Period of time elapsed before paying. If negative, means that this person has passed the 
     *               deadline and if positive, it was paid before it
     * @param deadline Product's deadline date
     * @param price Product's price per unit
     * 
     * @return final price (after discounts and/or penalties)
     */
    public int getsFinalPrice(int period, int deadline, int price) {
        if (period >= deadline) return this.calculatesFinalPriceBeforeProductDeadline(period, price);
        else if (period >= 0) return this.calculatesFinalPriceBeforeSaleDeadline(period, price);
        else if (Math.abs(period) <= deadline) return this.calculatesFinalPriceAfterSaleDeadline(period, price);
        else return this.calculatesFinalPriceAfterProductDeadline(period, price);
    }


}
