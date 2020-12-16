package woo.clients.rank;

import woo.clients.Client;
import java.io.Serializable;


/**
 * Represents an elite client's state.
 */
public class EliteState extends ClientState implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * EliteState class constructor
     *
     * @param client client
     * @param points client's points
     */
    public EliteState(Client client, int points) {
        super(client, points);
    }


    /**
     * Paid before hitting the product's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    public int calculatesFinalPriceBeforeProductDeadline(int period, int price) {
        return (int) Math.round(price * 0.90);
    }


    /**
     * Paid before hitting the sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    public int calculatesFinalPriceBeforeSaleDeadline(int period, int price) {
        return (int) Math.round(price * 0.90);
    }


    /**
     * Paid before product's deadline after sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    public int calculatesFinalPriceAfterSaleDeadline(int period, int price) {
        return price; 
    }


    /**
     * Paid after product's deadline after sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    public int calculatesFinalPriceAfterProductDeadline(int period, int price) {
        return price; 
    }


    /**
     * Checks, based on current accumulated points, if we should change our status to one of the others.
     *
     * @param period Period of time elapsed before paying. If negative, means that this person has passed the
     *               deadline and if positive, it was paid before it
     * */
    public void updateStatus(int period) {

        /* If period is equal or above zero, we will be looking for an upgrade. If not, we will demote our client based
        *  on elapsed time */
        if (period <= -16)
            this.getClient().setStatus(new SelectionState(this.getClient(), (int) (this.getPoints() * 0.25)));

    }

}
