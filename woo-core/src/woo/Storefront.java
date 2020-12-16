package woo;

import java.io.*;
import woo.exceptions.*;
import java.util.ArrayList;
import java.util.Collection;
import woo.products.Product;
import woo.clients.Client;
import woo.suppliers.Supplier;
import woo.transactions.Sale;


/**
 * Storefront: facade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();


  /**
   * Saves current state of the app in a file.
   * 
   * @throws IOException error of Input/Output
   * @throws FileNotFoundException invalid path
   * @throws MissingFileAssociationException application not associated with file
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException, UnavailableFileException {

    try (
      ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))
    ) {

      /* Blocks execution if file path was not specified */
      if (_filename.equals(""))
        throw new MissingFileAssociationException();

      /* Saves current session */
      out.writeObject(_store);

    } catch (FileNotFoundException e) {
      throw new UnavailableFileException(_filename);
    }

  }


  /**
   * Saves current state of the app in a file given by the user.
   * 
   * @param filename path to the file
   *
   * @throws MissingFileAssociationException application not associated with file
   * @throws IOException error of Input/Output
   * @throws FileNotFoundException invalid path
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException, UnavailableFileException {
    _filename = filename;
    save();
  }


  /**
   * Loads a file which holds a previous session of the app.
   * 
   * @param filename path to the file
   *
   * @throws UnavailableFileException problem accessing file
   */
  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {

    try (
      /* Gets an object to read the file */
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))
    ) {

      /* Saves filename to be used posteriorly */
      this.setAssociatedFile(filename);

      /* Loads previous session */
      _store = (Store) in.readObject();

    } catch (ClassNotFoundException | FileNotFoundException e) {
      throw new UnavailableFileException(filename);
    }

  }

  
  /**
   * Gets inputs from a text file.
   * 
   * @param textFile path to the text file that is going to be read
   *
   * @throws ImportFileException couldn't import file
   */
  public void importFile(String textFile) throws ImportFileException {
    try {
      _store.importFile(textFile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textFile);
    }
  }


  /**
   * Gets this session's current date.
   * 
   * @return date
   */
  public int getCurrentDate() { return _store.getCurrentDate(); }


  /**
   * Advance date.
   *
   * @param days integer representation of the passed number of days
   *
   * @throws DateException when the number of days to advance is a negative number
   */
  public void advanceDate(int days)  throws DateException { _store.advanceDate(days); }


  /**
   * Gets an unmodifiable collection with all the store's products. 
   * 
   * @return collections of products
   */
  public Collection<Product> getAllProducts() { return _store.getAllProducts(); }


  /**
   * Register a new client
   * @param id client's id
   * @param name client's name
   * @param address client's address
   *
   * @throws DuplicateKeyException client's key already exists
   */
  public void registerClient(String id, String name, String address) throws DuplicateKeyException {
    _store.registerClient(id, name, address);
  }


  /**
   * Register a new box.
   *
   * @param id            box's id
   * @param type          box's service type
   * @param supplierId    box's supplier id
   * @param price         box's price
   * @param criticalValue box's critical value
   *
   * @throws DuplicateKeyException box's key already exists
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   * @throws InvalidServiceTypeException box's service type doesn't exist
   */
  public void registerBox(String id, String type, String supplierId, int price, int criticalValue)
          throws DuplicateKeyException, InvalidSupplierKeyException, InvalidServiceTypeException {
    _store.registerBox(id, type, supplierId, price, criticalValue);
  }


  /**
   * Register a new container.
   *
   * @param id            container's id
   * @param type          container's service type
   * @param quality       container's service quality
   * @param supplierId    container's supplier id
   * @param price         container's price
   * @param criticalValue container's critical value
   *
   * @throws DuplicateKeyException container's key already exists
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   * @throws InvalidServiceTypeException container's service type doesn't exist
   * @throws InvalidServiceQualityException container's service quality doesn't exist
   */
  public void registerContainer(String id, String type, String quality, String supplierId, int price, int criticalValue)
          throws DuplicateKeyException, InvalidSupplierKeyException, InvalidServiceTypeException, InvalidServiceQualityException {
    _store.registerContainer(id, type, quality, supplierId, price, criticalValue);
  }


  /**
   * Register a new book.
   *
   * @param id            book's id
   * @param tittle        book's tittle
   * @param author        book's author
   * @param isbn          book's isbn
   * @param supplierId    book's supplier id
   * @param price         book's price
   *
   * @throws DuplicateKeyException book's key already exist
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   */
  public void registerBook(String id, String tittle, String author,  String isbn, String supplierId,
                          int price, int criticalValue) throws DuplicateKeyException, InvalidSupplierKeyException {
    _store.registerBook(id, tittle, author, isbn, supplierId, price, criticalValue);
  }


  /**
   * Register new supplier
   *
   * @param id      supplier's id
   * @param name    supplier's name
   * @param address supplier's address
   *
   * @throws DuplicateKeyException supplier's key already exists
   */
  public void registerSupplier(String id, String name, String address) throws DuplicateKeyException {
    _store.registerSupplier(id, name, address);
  }


  /**
   * Registers a new Sale.
   *
   * @param clientId client's id that holds this sale
   * @param deadline payment limit date
   * @param productId bought product's id
   * @param amount amount bought
   *
   * @throws InvalidProductKeyException product's key doesn't exist
   * @throws InvalidClientKeyException client's key doesn't exist
   * @throws InvalidProductAmountException when it is requested an unavailable amount of a product
   * */
  public void registerSale(String clientId, int deadline, String productId, int amount)
      throws InvalidProductKeyException, InvalidClientKeyException, InvalidProductAmountException{
    _store.registerSale(clientId, deadline, productId, amount);
  }


  /**
   * Registers a new Order.
   *
   * @param supplierId supplier's id that produces this product
   * @param productIds Array with all the products ids. Each index correlates with the same index in the other array
   * @param productAmounts Array with products amounts. Each index correlates with the same index in the other array
   *
   * @throws InvalidProductKeyException product's key doesn't exist
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   * @throws SupplierNotAuthorizedException supplier is not authorized to perform any transaction with the store
   * @throws ProductNotSuppliedException product is not supplied by input supplier
   * */
  public void registerOrder(String supplierId, ArrayList<String> productIds, ArrayList<Integer> productAmounts)
          throws InvalidProductKeyException, InvalidSupplierKeyException, SupplierNotAuthorizedException,
          ProductNotSuppliedException {
    _store.registerOrder(supplierId, productIds, productAmounts);
  }


  /**
   * Change product's price
   *
   * @param id      product's id
   * @param price   product's price
   *
   * @throws InvalidProductKeyException product's key doesn't exist
   */
  public void changePrice(String id, int price) throws InvalidProductKeyException {
    _store.changePrice(id, price);
  }


  /**
   * Activate/Deactivate supplier's activity
   *
   * @param id supplier's id
   *
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   */
  public boolean toggleTransactions(String id) throws InvalidSupplierKeyException {
    return _store.toggleTransactions(id);
  }


  /**
   * Shows a specific client.
   *
   * @param id client's key
   *
   * @return requested client represented as a string
   */
  public String showClient(String id) throws InvalidClientKeyException { return _store.showClient(id); }


  /**
   * Shows a specific client's notifications.
   *
   * @param id client's key
   *
   * @return requested client's notifications represented as a string
   */
  public String showClientNotifications(String id) throws InvalidClientKeyException { return _store.showClientNotifications(id); }


  /**
   * Gets all client's transactions.
   *
   * @param id client's key
   *
   * @return collection of sales
   * */
  public Collection<Sale> getAllClientTransactions(String id) throws InvalidClientKeyException { return _store.getAllClientTransactions(id); }


  /**
   * Gets an unmodifiable collection with all the store's clients.
   *
   * @return collections of clients
   */
  public Collection<Client> getAllClients(){ return _store.getAllClients(); }


  /**
   * Gets an unmodifiable collection with all the store's suppliers.
   *
   * @return collections of suppliers
   */
  public Collection<Supplier> getAllSuppliers(){ return _store.getAllSuppliers(); }


  /**
   * Gets associated file.
   *
   * @return if file exists, return true, else, false
   * */
  public boolean checkIfHasFileAssociated() { return ! _filename.equals(""); }


  /**
   * Sets associated file path.
   *
   * @param filePath new file path
   * */
  public void setAssociatedFile(String filePath) { _filename = filePath; }


  /**
   * Pays sale which holds input sale id.
   *
   * @param id sale id
   * */
  public void pay(int id) throws InvalidTransactionKeyException { _store.pay(id); }

  /**
   * Gets a visual representation of a transaction.
   *
   * @param id transaction's id
   *
   * @return string representation
   * */
  public String showTransaction(int id) throws InvalidTransactionKeyException { return _store.showTransaction(id); }


  /**
   * Gets a representation of all the past order with input supplier.
   *
   * @param id supplier id
   *
   * @return representation as a String
   * */
  public String getSupplierHistoryAsString(String id) throws InvalidSupplierKeyException {
    return _store.getSupplierHistoryAsString(id);
  }


  /**
   * Gets an unmodifiable collection with all the client's paid sales.
   *
   * @param id client's id
   *
   * @return client's paid sales
   *
   * @throws InvalidClientKeyException client's id doesn't exist
   */
  public Collection<Sale> lookupPaymentsByClient(String id) throws InvalidClientKeyException
  { return _store.lookupPaymentsByClient(id); }

  /**
   * Gets an unmodifiable collection with all the store's products under the price limit.
   *
   * @return collections of products
   */
  public Collection<Product> lookupProductsUnderTopPrice(int price) { return _store.lookupProductsUnderTopPrice(price); }


  /**
   * Allows/Inhibits client's notifications of this product
   *
   * @param clientId client's key
   * @param productId product's key
   *
   * @throws InvalidClientKeyException client's key doesn't exist
   * @throws InvalidProductKeyException product's key doesn't exist
   */
  public void toogleProductNotifications(String clientId, String productId) throws InvalidClientKeyException,
      InvalidProductKeyException { _store.toggleProductNotifications(clientId, productId); }

  /**
   * Get store's available balance (difference between all paid sales and orders).
   *
   * @return available balance as int
   */
  public int getAvailableBalance(){ return _store.getAvailableBalance();}


  /**
   * Get store's accounting balance (difference between all sales with discount/penalty and orders).
   *
   * @return accounting balance as int
   */
  public int getAccountingBalance(){ return _store.getAccountingBalance();}
}
