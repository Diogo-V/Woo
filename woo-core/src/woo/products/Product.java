package woo.products;

import woo.notifications.Bargain;
import woo.notifications.New;
import woo.Observer;
import woo.Subject;
import woo.notifications.Notifications;
import woo.notifications.delivery.App;
import woo.suppliers.Supplier;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;


/**
 * Representation of a product.
 */
public abstract class Product implements Serializable, Subject {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	/** Product's unique ID */
	private String id;

	/** Product's supplier */
	private Supplier supplier;

	/** Price of this product */
	private int price;

	/** Critical value of this product */
	private int critical;

	/** Stock of this product */
	private int stock;

	/** Deadline to pay for the product */
	private int deadline;

	/** Arrays of all the product's observers */
	private TreeMap<String, Observer> observers = new TreeMap<>();


	/**
	 * Product class constructor
	 *
	 * @param id product's id
	 * @param supplier product's supplier
	 * @param price product's price
	 * @param criticalValue product's critical value
	 * @param deadline product's deadline
	 */
	public Product(String id, Supplier supplier, int price, int criticalValue, int deadline) {
		this.id = id;
		this.price = price;
		this.critical = criticalValue;
		this.stock = 0;
		this.supplier = supplier;
		this.deadline = deadline;
	}


	/**
	 * Product class constructor
	 *
	 * @param id product's id
	 * @param supplier product's supplier
	 * @param price product's price
	 * @param criticalValue product's critical value
	 * @param stock product's stock
	 * @param deadline product's deadline
	 */
	public Product(String id, Supplier supplier, int price, int criticalValue, int stock, int deadline) {
		this.id = id;
		this.price = price;
		this.critical = criticalValue;
		this.stock = stock;
		this.supplier = supplier;
		this.deadline = deadline;
	}


	/**
	 * Gets the product's id.
	 *
	 * @return id as a string
	 */
	public String getId(){
		return this.id;
	}


	/**
	 * Gets the product's supplier.
	 *
	 * @return supplier
	 * */
	public Supplier getSupplier() { return this.supplier; }


	/**
	 * Gets the product's price.
	 *
	 * @return price as an int
	 */
	public int getPrice() {
		return this.price;
	}


	/**
	 * Gets the product's critical value.
	 *
	 * @return critical value as an int
	 * */
	public int getCritical() { return this.critical; }


	/**
	 * Gets the product's stock.
	 *
	 * @return stock as an int
	 * */
	public int getStock() { return this.stock; }


	/**
	 * Gets the deadline to pay for this product after being purchased.
	 *
	 * @return deadline as an int
	 * */
	public int getDeadline() { return this.deadline; }


	/**
	 * Sets new price for the product.
	 *
	 * @param newPrice product's new price
	 */
	public void setPrice(int newPrice) {
		/* Holds the old price */
		int oldPrice = this.price;

		/* Sets new price */
		this.price = newPrice;

		/* Only notify the observers if the new stock is lower that the old price */
		if (this.price < oldPrice)
			notifyObservers("BARGAIN");
	}


	/**
	 * Sets new stock for the product.
	 *
	 * @param newStock product's new stock
	 */
	public void setStock(int newStock) {
		/* Holds old stock */
		int oldStock = this.stock;

		/* Sets new stock for the product */
		this.stock = newStock;

		/* Only notify the observers if the old stock is 0 and the new stock is positive */
		if (oldStock == 0 && newStock > 0)
			notifyObservers("NEW");
	}


	/**
	 * Register a new observer for the product
	 *
	 * @param key observer's key
	 * @param o observer
	 */
	@Override
	public void registerObserver(String key, Observer o){
		this.observers.put(key, o);
	}


	/**
	 * Remove the observer for this product
	 *
	 * @param key observer's key
	 */
	@Override
	public void removeObserver(String key){
		this.observers.remove(key);
	}


	/**
	 * Notify the observer if the new price is lower than the older price or the stock changed from 0 to a positive value
	 *
	 * @param type notification's type
	 */
	@Override
	public void notifyObservers(String type){

		/* Creates a new notification */
		Notifications notification = createNotification(type);

		/* Sends the new notification to every interested observer and adds to the observer's notifications list */
		for (Map.Entry<String, Observer> entry : observers.entrySet()) {
			entry.getValue().getReceivedNotifications().add(notification);
		}

	}


	/**
	 * Creates a notification (bargain or new) accordingly to the parameter type. The notification
	 * delivery method is by app, by default.
	 *
	 * @param type notification's type
	 *
	 * @return notification
	 */
	public Notifications createNotification(String type){
		switch (type) {
			case "NEW":
				return new New(this, new App());

			case "BARGAIN":
				return new Bargain(this, new App());

			default:
				return null;
		}
	}


	/**
	 * Checks if the observer is already an observer of this product
	 *
	 * @param o observer
	 *
	 * @return boolean value
	 */
	public boolean checkIfObserverExists(Observer o){
		return observers.containsValue(o);
	}


	/**
	 * Add/removes this observer of product's observers list
	 *
	 * @param o observer
	 */
	public void toggleNotifications(String key, Observer o){
		/* If observer is already an observer of this product, we inhibit the notifications*/
		if(checkIfObserverExists(o)){
			removeObserver(key);
		}

		/* Else, we add as an observer */
		else{
			registerObserver(key, o);
		}
	}

}
