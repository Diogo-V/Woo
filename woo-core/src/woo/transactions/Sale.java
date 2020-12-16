package woo.transactions;

import woo.clients.Client;
import woo.products.Product;

import java.io.Serializable;


/**
 * Represents a transaction between a Client and the Store. Only holds a single product in a requested quantity.
 * */
public class Sale extends Transaction implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Client which holds this Sale */
  private Client client;

  /** Payment execution limit day for this sale */
  private int deadline;

  /** Holds sale status (paid or still to be paid) */
  private boolean isPaid = false;

  /** Holds price that would be paid if sale was paid today. Also holds paid price after payment */
  private int totalPriceProcessed = 0;


  /**
   * Sale class constructor.
   *
   * @param id transaction identifier
   * @param client client which holds this sale
   * @param deadline payment deadline
   * */
  public Sale(int id, Client client, int deadline) {
    super(id);
    this.client = client;
    this.deadline = deadline;
  }


  /**
   * Gets first (and only) element in our cart.
   *
   * @return class with product and it's amount
   * */
  private ProductAmountLink getElement() { return getCart().get(0); }


  /**
   * Gets client which holds this sale.
   *
   * @return client
   * */
  public Client getClient() { return client; }


  /**
   * Gets sale's deadline.
   *
   * @return integer holding deadline
   */
  public int getDeadline() { return deadline; }


  /**
   * Gets total cost of this sale if it was paid today. Or if already paid, the total price paid.
   *
   * @return total price
   * */
  public int getTotalPriceProcessed() { return this.totalPriceProcessed; }


  /**
   * Updates total cost of the sale with all the discounts and penalties. If sale is already paid, this won't be
   * updated and rather kept as the final price saved at time of payment. This function is mainly used by our Store
   * when returning a sale, to update the final price if it would be paid today.
   *
   * @param date current date
   * */
  public void updateTotalPriceProcessed(int date) {
    if (isPaid) return;
    totalPriceProcessed = getClient().simulatesPay(getPeriod(date, getDeadline()),
            getElement().getProduct().getDeadline(), getBasePrice());
  }


  /**
   * Gets sale payment status.
   *
   * @return boolean value accordingly
   * */
  public boolean isPaid() { return this.isPaid; }


  /**
   * Sets payment status as paid (true) and saves date of payment and the price paid.
   *
   * @param date date of payment
   * @param price total price of transaction (without discounts and penalties)
   * @param finalPrice total paid price (with discounts and penalties)
   * */
  private void setPaid(int date, int price, int finalPrice) {
    this.isPaid = true;
    setBasePrice(price);
    this.totalPriceProcessed = finalPrice;
    setDateOfPayment(date);
  }


  /**
   * Calculates period of time elapsed between today and the deadline.
   *
   * @param current today's date
   * @param deadline date when the sale should be already paid
   *
   * @return integer holding elapsed time
   */
  private int getPeriod(int current, int deadline) {
    return deadline - current;
  }


  /**
   * Adds an entry to our transaction. If product is already in cart, only adds to it's amount.
   *
   * @param product product to be added
   * @param amount requested amount of the product
   * */
  @Override
  public void add(Product product, int amount) {

    /* Adds product and its amount to our sale */
    super.add(product, amount);

    /* Sets sale price so that when we change a product's price, our sale's paid price doesn't change */
    setBasePrice(getElement().getTotalPrice());

  }


  /**
   * Pays this transaction.
   *
   * @param date date of payment
   * @param type true if the client is really paying or false if it's only a simulation of the payment
   *
   * @return sale's final price with penalty/discount
   * */
  public int pay(int date, boolean type) {

    /* Gets product's deadline after being purchased */
    int deadlineProduct = getElement().getProduct().getDeadline();

    /* Gets sale price before discounts and/or penalties */
    int price = getTotalPrice();

    /* Gets elapsed time between current day and deadline. If negative, means that this person has passed the
     * deadline and if positive, it was paid before it */
    int period = this.getPeriod(date, getDeadline());

    /* If type is "true" it means that the client is really paying the sale*/
    if (type) {

      /* Tells client to proceed with payment and returns the price paid */
      int finalPrice = getClient().pay(period, deadlineProduct, price);

      /* Sets sale status as paid, saves date of payment and it's final price and paid price */
      setPaid(date, price, finalPrice);

      return finalPrice;
    }

    /* Type is "false" and it's only a simulation of the payment, only used to calculate
    the sale's final price with discount/penalty, without updating the client's points,
    status or marking this sale as paid */
    else { return getClient().simulatesPay(period, deadlineProduct, price); }

  }


  @Override
  public String toString() {
    return super.toString() + getClient().getId() + "|" + getElement().getProduct().getId() + "|"
    + getElement().getAmount() + "|" + getBasePrice() + "|" + getTotalPriceProcessed() + "|" + getDeadline() +
    (isPaid() ? "|" + getDateOfPayment() + "\n" : "\n");
  }

}
