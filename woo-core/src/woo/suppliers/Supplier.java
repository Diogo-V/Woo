package woo.suppliers;


import woo.transactions.Order;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents a supplier. Store buys products from suppliers.
 * */
public class Supplier implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	/** Supplier's unique ID */
	private String id;

	/** Supplier's name */
	private String name;

	/** Supplier's address */
	private String address;

	/** Supplier's state is true if it's able to make transactions */
	private boolean status;

	/** Supplier's history of orders */
	private ArrayList<Order> history = new ArrayList<>();


	/**
	 * Supplier class constructor
	 *
	 * @param id supplier id
	 * @param name supplier's name
	 * @param address supplier's address
	 * */
	public Supplier(String id, String name, String address){
		this.id = id;
		this.name = name;
		this.address = address;
		this.status = true;
	}


	/**
	 * Returns supplier's id.
	 * 
	 * @return id as a string
	 */
	public String getId(){ return this.id; }


	/**
	 * Checks if the supplier is active.
	 *
	 * @return boolean
	 */
	public boolean getStatus(){ return this.status; }


	/**
	 * Gets a string with all the transactions made by this supplier.
	 *
	 * @return representation of all the orders
	 * */
	public String getHistoryAsString() {

		/* Builds our string */
		StringBuilder result = new StringBuilder();

		/* Iterates over each order and gets it's representation */
		for (Order order: history) {
			result.append(order);  /* We can pass any value since it is going to be ignored */
		}

		/* Returns completed string */
		return result.toString();
	}


	/**
	 * Toggles state. If active, becomes inactive and vice-versa.
	 *
	 * @return new state
	 * */
	public boolean toggleStatus() { this.status = ! this.status; return this.status; }


	/**
	 * Adds a new entry to supplier's history.
	 *
	 * @param order new order
	 * */
	public void addHistory(Order order) { history.add(order); }


	/**
	 * Gets a string representation of the supplier.
	 * 
	 * @return return a visual representation of the supplier as a string
	 */
	@Override
	public String toString() {
		return this.id + "|" + this.name + "|" + this.address + "|";
	}

}
