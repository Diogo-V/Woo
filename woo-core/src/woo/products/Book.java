package woo.products;

import woo.suppliers.Supplier;

/**
 * Represents a book.
 */
public class Book extends Product {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	/** Book's tittle */
	private String title;

	/** Book's author */
	private String author;

	/** Book's ISBN */
	private String isbn;


	/**
	 * Book class constructor.
	 *
	 * @param id book's id
	 * @param tittle book's title
	 * @param author book's author
	 * @param isbn book's isbn
	 * @param supplier book's supplier
	 * @param price book's price
	 * @param criticalValue book's critical value
	 */
	public Book(String id, String tittle, String author,  String isbn, Supplier supplier,
							int price, int criticalValue){
		super(id, supplier, price, criticalValue,3);
		this.title = tittle;
		this.author = author;
		this.isbn = isbn;
	}


	/**
	 * Book class constructor.
	 *
	 * @param id book's id
	 * @param tittle book's title
	 * @param author book's author
	 * @param isbn book's isbn
	 * @param supplier book's supplier
	 * @param price book's price
	 * @param criticalValue book's critical value
	 * @param stock book's stock
	 */
	public Book(String id, String tittle, String author,  String isbn, Supplier supplier,
				int price, int criticalValue, int stock){
		super(id, supplier, price, criticalValue, stock, 3);
		this.title = tittle;
		this.author = author;
		this.isbn = isbn;
	}


	/**
	 * Gets a string representation of the book.
	 * 
	 * @return return a visual representation of the book as a string
	 */
	@Override
	public String toString(){
		return "BOOK|" + this.getId() + "|" + this.getSupplier().getId() + "|" + this.getPrice()
		+ "|" + this.getCritical() + "|" + this.getStock() + "|" + this.title + "|" + this.author
		+ "|" + this.isbn;
	}

}