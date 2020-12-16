package woo.clients.rank;

import woo.clients.Client;
import woo.clients.Constants;
import java.io.Serializable;


/**
 * Represents a selection client's state.
 */
public class SelectionState extends ClientState implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * SelectionState class constructor
     *
     * @param client client
     * @param points client's points
     */
    public SelectionState(Client client, int points) {
        super(client, points);
    }


    /**
     * Paid before hitting the product deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    int calculatesFinalPriceBeforeProductDeadline(int period, int price) { return (int) Math.round(price * 0.90); }


    /**
     * Paid before hitting the deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    int calculatesFinalPriceBeforeSaleDeadline(int period, int price) {
        return period <= 2 ? (int) Math.round(price * 0.95) : price;
    }


    /**
     * Paid before product's deadline after sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    int calculatesFinalPriceAfterSaleDeadline(int period, int price) {
        return period == -1 ? price : (int) Math.round(price * (1 + 0.02 * Math.abs(period)));
    }


    /**
     * Paid after product's deadline after sale's deadline.
     *
     * @param period Period of time elapsed before paying
     * @param price Product's price per unit
     *
     * @return final price (after discounts and penalties)
     */
    int calculatesFinalPriceAfterProductDeadline(int period, int price) {
        return (int) Math.round(price * (1 + 0.05 * Math.abs(period)));
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
        if (period >= 0) {
            if (this.getPoints() >= Constants.ELITE_BOUND)
                this.getClient().setStatus(new EliteState(this.getClient(), this.getPoints()));
        } else if (period <= -3) {
            this.getClient().setStatus(new NormalState(this.getClient(), (int) (this.getPoints() * 0.10)));
        }

    }

}
