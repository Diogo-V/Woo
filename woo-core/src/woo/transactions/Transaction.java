package woo.transactions;

import woo.products.Product;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents an abstract transaction holding a set of products and their bought/sold quantities.
 * */
public abstract class Transaction implements Serializable {


    /**
     * Represents a connection between a product and it's quantity. Cannot even be used outside of transactions package.
     * Mainly used to solve the problem of association between this 2 components. Other solution would be using a 2-dim
     * ArrayList of Objects but this seems more standard.
     * */
    class ProductAmountLink implements Serializable {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202009192006L;

        /** Product in this link */
        private Product product;

        /** Product's amount */
        private int amount;


        /**
         * ProductAmountLink class constructor.
         *
         * @param product product in this link
         * @param amount amount of product
         * */
        public ProductAmountLink(Product product, int amount) {
            this.product = product;
            this.amount = amount;
        }


        /**
         * Gets link's product.
         *
         * @return product
         * */
        public Product getProduct(){ return this.product; }


        /**
         * Gets product amount.
         *
         * @return amount of product
         * */
        public int getAmount(){
            return this.amount;
        }


        /**
         * Calculates total price of this link.
         *
         * @return total price
         * */
        public int getTotalPrice() { return getAmount() * getProduct().getPrice(); }


        /**
         * Sets amount of product.
         *
         * @param amount new amount
         * */
        public void setAmount(int amount) { this.amount = amount; }

    }


    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /** Transaction identifier */
    private int id;

    /** Holds bought products and it's amount. If it is a sale, only holds one element, if it is an order, can have
     *  as many elements as needed. Simulates a 'cart' */
    private ArrayList<ProductAmountLink> cart = new ArrayList<>();

    /** Holds date of payment (if has been payed) */
    private int dateOfPayment = -1;

    /** Order total price. Saved so that, when a product changes it's price, this won't change */
    private int basePrice = 0;


    /**
     * Transaction class constructor.
     *
     * @param id transaction's id
     * */
    public Transaction(int id) { this.id = id; }


    /**
     * Gets total price of all the elements associated with this transaction at the time of registration.
     * Does not include discounts or penalties.
     *
     * @return price
     * */
    public int getBasePrice() { return this.basePrice; }


    /**
     * Gets transaction's total price. Can change based on price fluctuations.
     *
     * @return transaction price
     * */
    public int getTotalPrice() {
        int result = 0;
        for (ProductAmountLink link: cart) result += link.getTotalPrice();
        return result;
    }


    /**
     * Gets transaction's id.
     *
     * @return identifier
     * */
    public int getId() { return this.id; }


    /**
     * Gets date of payment.
     *
     * @return date
     * */
    public int getDateOfPayment() { return dateOfPayment; }


    /**
     * Gets an arraylist with all the products and it's amounts in this transaction.
     *
     * @return cart
     * */
    public ArrayList<ProductAmountLink> getCart() { return this.cart; }


    /**
     * Sets date of payment.
     *
     * @param date date of payment
     * */
    public void setDateOfPayment(int date) { this.dateOfPayment = date; }


    /**
     * Sets price of transaction.
     *
     * @param basePrice new price
     * */
    public void setBasePrice(int basePrice) { this.basePrice = basePrice; }


    /**
     * Adds an entry to our transaction. If product is already in cart, only adds to it's amount.
     *
     * @param product product to be added
     * @param amount requested amount of the product
     * */
    public void add(Product product, int amount) {

        /* Checks if product is already in cart and if so, updates it's amount */
        for (ProductAmountLink link: cart)
            if (link.getProduct().getId().equals(product.getId())) {
                link.setAmount(link.getAmount() + amount);
                return ;
            }

        /* Since we didn't find this product, we create and add a link to the cart */
        cart.add(new ProductAmountLink(product, amount));
    }


    @Override
    public String toString() {
        return getId() + "|";
    }

}
