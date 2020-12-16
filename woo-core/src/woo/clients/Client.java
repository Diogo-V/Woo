package woo.clients;

import woo.Observer;
import woo.clients.rank.NormalState;
import woo.clients.rank.ClientState;
import woo.transactions.Sale;
import java.util.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Represents a client.
 * */
public class Client extends Observer implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	/** Client's unique ID */
	private String id = "";

	/** Client's name */
	private String name = "";

	/** Client's address */
	private String address = "";

	/** Client's total paid */
	private int totalPaid = 0;

	/** Tree map of sales made by this client */
	private TreeMap<Integer, Sale> sales = new TreeMap<>();

	/** Client's rank (can be normal, selection and elite depending on the number of points) */
	private ClientState status = new NormalState(this, 0);


	/**
	 * Order class constructor.
	 *
	 * @param id client's key
	 * @param name client's name
	 * @param address client's address
	 */
	public Client(String id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}


	/**
	 * Gets the client's id.
	 *
	 * @return id as a string
	 */
	public String getId(){
		return this.id;
	}


	/**
	 * Gets total value paid by this client (with discounts and penalties).
	 *
	 * @return total paid
	 * */
	private int getTotalPaid() {
		return this.totalPaid;
	}


	/**
	 * Gets all record of all client's sales.
	 *
	 * @return treemap containing all sales
	 * */
	public TreeMap<Integer, Sale> getSales() {  // FIXME: verificar se isto não é para tambem ter as unpaidSales!!!!
		return this.sales;
	}


	/**
	 * Gets the client's status.
	 *
	 * @return status as a rank
	 */
	public ClientState getStatus(){
		return this.status;
	}


	/**
	 * Returns the client's status.
	 *
	 * @param state new client rank
	 */
	public void setStatus(ClientState state){
		this.status = state;
	}


	/**
	 * Adds a new record of paid sales to this client.
	 *
	 * @param sale new sale record
	 * */
	public void addSaleRecord(Sale sale) { this.sales.put(sale.getId(), sale); }


	/**
	 * Processes a payment.
	 *
	 * @param period Period of time elapsed before paying. If negative, means that this person has passed the
	 *               time deadline and if positive, it was paid before it
	 * @param deadline product's deadline date of payment
	 * @param price sale total price before discounts and penalties
	 *
	 * @return sale's final price as integer
	 */
	public int pay(int period, int deadline, int price) {

		/* Processes payment and updates status */
		int finalPrice = this.getStatus().processPayment(period, deadline, price);

		/* Saves total payed by client */
		totalPaid += finalPrice;

		return finalPrice;

	}


	/**
	 * Simulates sale's payment (with discount/penalties) without updating the client's points or status.
	 *
	 * @param currentDate today's date
	 * @param deadline product's deadline date of payment
	 * @param price sale total price before discounts and penalties
	 *
	 * @return sale's final price
	 */
	public int simulatesPay(int currentDate, int deadline, int price){
		/* Calculates the final price of the sales with penalty/discount */
		return this.getStatus().getsFinalPrice(currentDate, deadline, price);
	}


	/**
	 * Gets total value of all the bought products by this client (without discounts and penalties).
	 *
	 * @return total purchased value
	 * */
	private int getPurchasesPrice() {
		AtomicInteger total = new AtomicInteger();
		sales.forEach((integer, sale) -> total.addAndGet(sale.getTotalPrice()));
		return total.get();
	}


	/**
	 * Gets all record of all client's paid sales.
	 *
	 * @return treemap containing all paid sales
	 * */
	public TreeMap<Integer, Sale> getPaidSales(){
		TreeMap<Integer, Sale> paid = new TreeMap<>();

		for(Map.Entry<Integer,Sale> entry : sales.entrySet()) {
			Integer key = entry.getKey();
			Sale value = entry.getValue();
			if (value.isPaid()){
				paid.put(key, value);
			}
		}

		return paid;
	}


	/**
	 * Gets a string representation of the client.
	 * 
	 * @return return a visual representation of the client as a string
	 */
	public String toString(){
		return this.getId() + "|" + this.name + "|" + this.address + "|" +
		this.getStatus().getClass().getSimpleName().toUpperCase().replace("STATE", "") + "|" +
		this.getPurchasesPrice() + "|" + this.getTotalPaid();
	}

}
