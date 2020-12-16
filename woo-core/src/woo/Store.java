package woo;

import java.io.*;
import woo.clients.Client;
import woo.products.*;
import woo.products.services.type.*;
import woo.products.services.quality.*;
import woo.exceptions.*;
import woo.products.Product;
import woo.suppliers.Supplier;
import woo.transactions.Order;
import woo.transactions.Sale;
import java.util.*;
import java.util.stream.IntStream;


/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Holds the next transaction's id */
  private int transactionId = 0;

  /** Holds current date */
  private int date = 0;

  /** Holds record of all the store's clients */
  private TreeMap<String, Client> clients = new TreeMap<>();

  /** Holds record of all the store's products */
  private TreeMap<String, Product> products = new TreeMap<>();

  /** Holds record of all the store's sales */
  private TreeMap<Integer, Sale> sales = new TreeMap<>();

  /** Holds record of all the store's orders */
  private TreeMap<Integer, Order> orders = new TreeMap<>();

  /** Holds record of all the store's suppliers */
  private TreeMap<String, Supplier> suppliers = new TreeMap<>();

  /** Holds store's available balance (difference between all paid sales and orders) */
  private int availableBalance = 0;


  /**
   * @param textFile filename to be loaded
   * @throws IOException       error of input/output
   * @throws BadEntryException Unknown import file entry
   */
  void importFile(String textFile) throws IOException, BadEntryException /* FIXME: add another exceptions */ {

    try {

      /* Gets a buffer for reading */
      BufferedReader reader = new BufferedReader(new FileReader(textFile));

      /* Reads a line from the buffer */
      String line = reader.readLine();

      while (line != null) {

        /* Gets all the fields by splitting the string in all '|' */
        String[] fields = line.split("\\|");

        /* Checks which class it is referring to */
        switch (fields[0]) {

          // CLIENT|id|nome|endereço
          case "CLIENT": {
            Client client = new Client(fields[1], fields[2], fields[3]);
            clients.put(fields[1].toLowerCase(), client);

            /* Client is now observing all products */
            addObserverToProductsObserversList(client.getId(), client);

            break;
          }

          // SUPPLIER|id|name|address
          case "SUPPLIER": {
            Supplier supplier = new Supplier(fields[1], fields[2], fields[3]);
            suppliers.put(fields[1].toLowerCase(), supplier);
            break;
          }

          // BOX|id|tipo-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
          case "BOX": {
            Box box = new Box(fields[1], parseServiceType(fields[2]), suppliers.get(fields[3].toLowerCase()),
                Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));
            products.put(fields[1].toLowerCase(), box);

            /* Box is now subject of all clients */
            addAllObserversToProductObserversList(box);

            break;
          }

          // CONTAINER|id|tipo-de-serviço|nível-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
          case "CONTAINER": {
            Container container = new Container(fields[1], parseServiceType(fields[2]),
                parseServiceQuality(fields[3]), suppliers.get(fields[4].toLowerCase()), Integer.parseInt(fields[5]),
                Integer.parseInt(fields[6]), Integer.parseInt(fields[7]));
            products.put(fields[1].toLowerCase(), container);

            /* Container is now subject of all clients */
            addAllObserversToProductObserversList(container);

            break;
          }

          // BOOK|id|título|autor|isbn|id-fornecedor|preço|valor-crítico|exemplares
          case "BOOK": {
            Book book = new Book(fields[1], fields[2], fields[3], fields[4], suppliers.get(fields[5].toLowerCase()),
                Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), Integer.parseInt(fields[8]));
            products.put(fields[1].toLowerCase(), book);

            /* Book is now subject of all clients */
            addAllObserversToProductObserversList(book);

            break;
          }

          /* Throws error when it didn't match any of the classes*/
          default: {
            throw new BadEntryException(fields[0]);
          }

        }

        /* Reads another line from the file */
        line = reader.readLine();

      }

      /* Closes file after reading all the line */
      reader.close();

      /* Error catching. We should not send an error from core but this an error from the core */
    } catch (IOException | BadEntryException e) {
      e.printStackTrace();
    }

  }


  /**
   * Evaluates a string representing a service and then creates a service accordingly.
   *
   * @param type String that represents a service
   * @return if service is valid, return an instance of that object. If not, returns null
   */
  private ServiceType parseServiceType(String type) {
    switch (type) {
      case "AIR":
        return new Air();
      case "EXPRESS":
        return new Express();
      case "PERSONAL":
        return new Personal();
      case "NORMAL":
        return new Normal();
      default:
        return null;
    }
  }


  /**
   * Evaluates a string representing a quality of service and then creates it accordingly.
   *
   * @param quality String that represents a quality
   * @return if service is valid, return an instance of that object. If not, returns null
   */
  private ServiceQuality parseServiceQuality(String quality) {
    switch (quality) {
      case "B4":
        return new B4();
      case "C4":
        return new C4();
      case "C5":
        return new C5();
      case "DL":
        return new Dl();
      default:
        return null;
    }
  }


  /**
   * Add observer to all product's observers list.
   *
   * @param o observer
   */
  private void addObserverToProductsObserversList (String key, Observer o){

    /* Observer is now observing all products */
    for (Map.Entry<String, Product> entry : products.entrySet()) {
      Product value = entry.getValue();
      value.registerObserver(key, o);
    }

  }


  /**
   * Add all observers existing in the store as new product's observers list
   *
   * @param p product
   */
  private void addAllObserversToProductObserversList(Product p){

    /* Product is now subject of all clients */
    for (Map.Entry<String, Client> entry : clients.entrySet()) {
      Client client = entry.getValue();
      p.registerObserver(client.getId(), client);
    }

  }


  /**
   * Gets this session's current date.
   *
   * @return date
   */
  public int getCurrentDate() {
    return this.date;
  }


  /**
   * Advance Date by a requested amount of days.
   *
   * @param days number of days to advance
   * @throws DateException number of days to advance is a negative number
   */
  public void advanceDate(int days) throws DateException {
    if (days < 0) throw new DateException();

    this.date = getCurrentDate() + days;
  }


  /**
   * Gets a specific client.
   *
   * @param id client's key
   * @return requested client
   */
  public Client getClient(String id) {
    return clients.get(id.toLowerCase());
  }


  /**
   * Gets an unmodifiable collection with all the store's clients.
   *
   * @return collections of clients
   */
  public Collection<Client> getAllClients() {
    return Collections.unmodifiableCollection(clients.values());
  }



  /**
   * Gets a specific product.
   *
   * @param id product's key
   * @return requested product
   */
  public Product getProduct(String id) {
    return products.get(id.toLowerCase());
  }


  /**
   * Gets an unmodifiable collection with all the store's products.
   *
   * @return collections of products
   */
  public Collection<Product> getAllProducts() {
    return Collections.unmodifiableCollection(products.values());
  }


  /**
   * Gets a specific supplier.
   *
   * @param id supplier's key
   * @return requested supplier
   */
  public Supplier getSupplier(String id) {
    return suppliers.get(id.toLowerCase());
  }


  /**
   * Gets an unmodifiable collection with all the store's suppliers.
   *
   * @return collections of suppliers
   */
  public Collection<Supplier> getAllSuppliers() {
    return Collections.unmodifiableCollection(suppliers.values());
  }


  /**
   * Gets requested order
   *
   * @param id order's id
   * @return order
   */
  public Order getOrder(int id){
    return orders.get(id);
  }


  /**
   * Get requested sale
   *
   * @param id sale's id
   * @return sale
   */
  public Sale getSale(int id){
    Sale s = sales.get(id);
    if(s != null)
      s.updateTotalPriceProcessed(getCurrentDate());
    return s;
  }


  /**
   * Get store's available balance (difference between all paid sales and orders)
   *
   * @return available balance as int
   */
  public int getAvailableBalance() { return availableBalance; }


  /**
   * Get store's accounting balance (difference between all sales with discount/penalty and orders).
   *
   * @return accounting balance as int
   */
  public int getAccountingBalance() {
    int unpaidSales = 0;

    for (Map.Entry<Integer, Sale> entry : sales.entrySet()) {
      Sale s = entry.getValue();
      if (!s.isPaid()) unpaidSales += s.pay(getCurrentDate(), false);

    }

    return availableBalance + unpaidSales;
  }


  /**
   * Register a new client.
   *
   * @param id      client's id
   * @param name    client's name
   * @param address client's address
   * @throws DuplicateKeyException client's key already exists
   */
  public void registerClient(String id, String name, String address) throws DuplicateKeyException {

    /* We can't register a client if it already exists */
    if (checkIfClientExists(id)) throw new DuplicateKeyException(id);

    /* Register client*/
    Client client = new Client(id, name, address);
    clients.put(id.toLowerCase(), client);

    /* Client is now observing all products */
    addObserverToProductsObserversList(id, client);

  }


  /**
   * Register a new box.
   *
   * @param id            box's id
   * @param type          box's service type
   * @param supplierId    box's supplier id
   * @param price         box's price
   * @param criticalValue box's critical value
   * @throws DuplicateKeyException       box's key already exists
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   * @throws InvalidServiceTypeException box's service type doesn't exist
   */
  public void registerBox(String id, String type, String supplierId, int price, int criticalValue)
      throws DuplicateKeyException, InvalidSupplierKeyException, InvalidServiceTypeException {

    /* We can't register a box if it already exists */
    if (checkIfProductExists(id)) throw new DuplicateKeyException(id);

    /* We can't register a box if the supplier doesn't exist */
    if (!checkIfSupplierExists(supplierId)) throw new InvalidSupplierKeyException(supplierId);

    /* We can't register a box if the service doesn't exist */
    if (!checkIfServiceTypeExists(type)) throw new InvalidServiceTypeException();

    /* Register box */
    Box box = new Box(id, parseServiceType(type), suppliers.get(supplierId.toLowerCase()), price, criticalValue);
    products.put(id.toLowerCase(), box);

    /* Box is now subject of all clients */
    addAllObserversToProductObserversList(box);

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
   * @throws DuplicateKeyException          container's key already exists
   * @throws InvalidSupplierKeyException    supplier's key doesn't exist
   * @throws InvalidServiceTypeException    container's service type doesn't exist
   * @throws InvalidServiceQualityException container's service quality doesn't exist
   */
  public void registerContainer(String id, String type, String quality, String supplierId, int price, int criticalValue)
      throws DuplicateKeyException, InvalidSupplierKeyException, InvalidServiceTypeException, InvalidServiceQualityException {

    /* We can't register a container if it already exists */
    if (checkIfProductExists(id)) throw new DuplicateKeyException(id);

    /* We can't register a container if the supplier doesn't exist */
    if (!checkIfSupplierExists(supplierId)) throw new InvalidSupplierKeyException(supplierId);

    /* We can't register a container if the service doesn't exist */
    if (!checkIfServiceTypeExists(type)) throw new InvalidServiceTypeException();

    /* We can't register a container if the service doesn't exist */
    if (!checkIfServiceQualityExists(quality)) throw new InvalidServiceQualityException();

    /* Register container */
    Container container = new Container(id, parseServiceType(type), parseServiceQuality(quality),
        suppliers.get(supplierId.toLowerCase()), price, criticalValue);
    products.put(id.toLowerCase(), container);

    /* Container is now subject of all clients */
    addAllObserversToProductObserversList(container);

  }


  /**
   * Register a new book.
   *
   * @param id         book's id
   * @param tittle     book's tittle
   * @param author     book's author
   * @param isbn       book's isbn
   * @param supplierId book's supplier id
   * @param price      book's price
   * @throws DuplicateKeyException       book's key already exist
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   */
  public void registerBook(String id, String tittle, String author, String isbn, String supplierId,
                           int price, int criticalValue) throws DuplicateKeyException, InvalidSupplierKeyException {

    /* We can't register a book if it already exists */
    if (checkIfProductExists(id)) throw new DuplicateKeyException(id);

    /* We can't register a book if the supplier doesn't exist */
    if (!checkIfSupplierExists(supplierId)) throw new InvalidSupplierKeyException(supplierId);

    /* Register book */
    Book book = new Book(id, tittle, author, isbn, suppliers.get(supplierId.toLowerCase()), price, criticalValue);
    products.put(id.toLowerCase(), book);

    /* Book is now subject of all clients */
    addAllObserversToProductObserversList(book);

  }


  /**
   * Register new supplier
   *
   * @param id      supplier's id
   * @param name    supplier's name
   * @param address supplier's address
   * @throws DuplicateKeyException supplier's key already exists
   */
  public void registerSupplier(String id, String name, String address) throws DuplicateKeyException {
    /* We can't register a supplier that already exists */
    if (checkIfSupplierExists(id)) throw new DuplicateKeyException(id);

    /* Register new supplier */
    Supplier supplier = new Supplier(id, name, address);
    suppliers.put(id.toLowerCase(), supplier);

  }


  /**
   * Registers a new Sale
   *
   * @param clientId  client's id that holds this sale
   * @param deadline  payment deadline
   * @param productId bought product's id
   * @param amount    amount bought
   * @throws InvalidProductKeyException    product's key doesn't exist
   * @throws InvalidClientKeyException     client's key doesn't exist
   * @throws InvalidProductAmountException when it is requested an unavailable amount of a product
   */
  public void registerSale(String clientId, int deadline, String productId, int amount)
      throws InvalidProductKeyException, InvalidClientKeyException, InvalidProductAmountException {

    /* Verifies all the info before proceeding */
    if (!checkIfProductExists(productId))
      throw new InvalidProductKeyException(productId);
    if (!checkIfClientExists(clientId))
      throw new InvalidClientKeyException(clientId);
    if (getProduct(productId).getStock() < amount)
      throw new InvalidProductAmountException(amount, getProduct(productId).getStock());

    /* Gets product that is going to be added, the associated client and creates a sale */
    Product product = getProduct(productId);
    Client client = getClient(clientId);
    Sale sale = new Sale(transactionId, client, deadline);

    /* Adds product to sale and updates product stock (since we sold some of it) */
    sale.add(product, amount);
    product.setStock(product.getStock() - amount);

    /* Inserts sale in sales record */
    sales.put(transactionId, sale);
    transactionId++;

    /* Adds sale to client's history of sales */
    client.addSaleRecord(sale);

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

    /* Verifies supplier info before proceeding */
    if (!checkIfSupplierExists(supplierId)) throw new InvalidSupplierKeyException(supplierId);
    if (!checkIfSupplierIsAuthorized(supplierId)) throw new SupplierNotAuthorizedException(supplierId);

    /* Iterates over each product key and checks if not only the products key refers to a valid product but also
     *  if the product is supplied by input supplier */
    for (String productId : productIds) {
      if (!checkIfProductExists(productId)) throw new InvalidProductKeyException(productId);
      if (!checkIfProductHasValidSupplier(productId, supplierId)) throw new ProductNotSuppliedException(productId, supplierId);
    }

    /* Since all the info is valid, we can now start by creating an empty order */
    Order order = new Order(transactionId, getSupplier(supplierId));
    getSupplier(supplierId).addHistory(order);

    /* Iterates over each array and adds all the components to our order. Each index in productIds correlates with
     *  the same index in productAmounts to create a valid product + amount combo */
    IntStream.range(0, productIds.size()).forEach(index -> {
      Product product = getProduct(productIds.get(index));
      int amount = productAmounts.get(index);
      order.add(product, amount);
      product.setStock(product.getStock() + amount);
    });

    /* Adds order to the orders array and increments transaction's identifier. Also finishes order */
    order.finish(getCurrentDate());
    orders.put(transactionId, order);
    transactionId++;

    /* Deducts the order price from the availableBalance */
    availableBalance -= order.getBasePrice();

  }


  /**
   * Checks if clients already exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfClientExists(String key) {
    return getClient(key) != null;
  }


  /**
   * Checks if supplier is authorized to do transactions with the store.
   *
   * @param id supplier's identifier
   * @return boolean value accordingly
   */
  public boolean checkIfSupplierIsAuthorized(String id) {
    return getSupplier(id).getStatus();
  }


  /**
   * Checks if product already exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfProductExists(String key) {
    return getProduct(key) != null;
  }


  /**
   * Checks if supplier already exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfSupplierExists(String key) {
    return getSupplier(key) != null;
  }


  /**
   * Checks if this product is supplied by the input supplier
   *
   * @param productId  product's id
   * @param supplierId supplier's id
   * @return boolean value
   */
  public boolean checkIfProductHasValidSupplier(String productId, String supplierId) {
    return getProduct(productId).getSupplier().getId().equals(supplierId);
  }


  /**
   * Checks if sale already exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfSaleExists(int key) {
    return getSale(key) != null;
  }


  /**
   * Checks if order already exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfOrderExists(int key) {
    return getOrder(key) != null;
  }


  /**
   * Checks if service's type exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfServiceTypeExists(String service) {
    return service.equals("NORMAL") || service.equals("AIR") || service.equals("EXPRESS")
        || service.equals("PERSONAL");
  }


  /**
   * Checks if service's quality exists in the store.
   *
   * @return boolean value
   */
  public boolean checkIfServiceQualityExists(String service) {
    return service.equals("B4") || service.equals("C4") || service.equals("C5")
        || service.equals("DL");
  }


  /**
   * Shows a specific client.
   *
   * @param id client's key
   * @return requested client represented as a string
   * @throws InvalidClientKeyException client's key doesn't exist
   */
  public String showClient(String id) throws InvalidClientKeyException {
    if (!checkIfClientExists(id)) throw new InvalidClientKeyException(id);  /* If it doesn't exist, we throw an error */
    return getClient(id).toString();
  }


  /**
   * Shows a specific client's notifications.
   *
   * @param id client's key
   * @return requested client's notifications represented as a string
   * @throws InvalidClientKeyException client's key doesn't exist
   */
  public String showClientNotifications(String id) throws InvalidClientKeyException {
    if (!checkIfClientExists(id)) throw new InvalidClientKeyException(id);  /* If it doesn't exist, we throw an error */
    return getClient(id).sendReceivedNotifications();
  }


  /**
   * Gets all client's transactions.
   *
   * @param id client's key
   * @return collection of sales
   * @throws InvalidClientKeyException client's key doesn't exist
   */
  public Collection<Sale> getAllClientTransactions(String id) throws InvalidClientKeyException {
    /* If it doesn't exist, we throw an error */
    if (!checkIfClientExists(id)) throw new InvalidClientKeyException(id);
    return Collections.unmodifiableCollection(getClient(id).getSales().values());
  }


  /**
   * Pays sale which holds input sale id.
   *
   * @param id sale id
   */
  public void pay(int id) throws InvalidTransactionKeyException {
    /* If transaction is an order, since orders are paid immediately we don't need to do it again.*/
    if(checkIfOrderExists(id))
      return;

    /* Checks if sale exists. If not, throws exception */
    if (!checkIfSaleExists(id))
      throw new InvalidTransactionKeyException(id);

    /* Gets sale */
    Sale sale = getSale(id);

    /* Checks if sale is already paid */
    if (sale.isPaid()) return;

    /* Pays the sale and updates the available balance of this store */
    availableBalance += sale.pay(getCurrentDate(), true);

  }


  /**
   * Change product's price
   *
   * @param id    product's id
   * @param price product's price
   * @throws InvalidProductKeyException product's key doesn't exist
   */
  public void changePrice(String id, int price) throws InvalidProductKeyException {
    /* We can't change the price of a product that doesn't exist */
    if (!checkIfProductExists(id)) throw new InvalidProductKeyException(id);

    /* Price must be positive */
    if (price <= 0) {
      return;
    }

    /* Changes price */
    getProduct(id).setPrice(price);
  }


  /**
   * Gets a visual representation of a transaction.
   *
   * @param id transaction's id
   * @return string representation
   */
  public String showTransaction(int id) throws InvalidTransactionKeyException {
    /* Transaction is not a sale */
    if(!checkIfSaleExists(id))
      /* Transaction is neither an order. It means that the transaction doesn't exist. */
      if(!checkIfOrderExists(id))
        throw new InvalidTransactionKeyException(id);

      /* Transaction is an order */
      else
        /* Gets order's visual representation */
        return getOrder(id).toString();

    /* Transaction is a sale */
    else
      /* Gets sale's visual representation */
      return getSale(id).toString();

  }


  /**
   * Gets a representation of all the past order with input supplier.
   *
   * @param id supplier id
   * @return representation as a String
   */
  public String getSupplierHistoryAsString(String id) throws InvalidSupplierKeyException {

    /* We can't register a supplier that already exist */
    if (!checkIfSupplierExists(id)) throw new InvalidSupplierKeyException(id);

    /* Returns string representing all past orders */
    return getSupplier(id).getHistoryAsString();
  }


  /**
   * Gets an unmodifiable collection with all the client's paid sales.
   *
   * @param id client's id
   * @return client's paid sales
   * @throws InvalidClientKeyException client's id doesn't exist
   */
  public Collection<Sale> lookupPaymentsByClient(String id) throws InvalidClientKeyException {
    if (!checkIfClientExists(id)) throw new InvalidClientKeyException(id);

    return Collections.unmodifiableCollection(getClient(id).getPaidSales().values());

  }


  /**
   * Gets an unmodifiable collection with all the store's products under the price limit.
   *
   * @param price price limit
   * @return collections of products
   */
  public Collection<Product> lookupProductsUnderTopPrice(int price) {
    TreeMap<String, Product> productsUnderPrice = new TreeMap<>();

    for (Map.Entry<String, Product> entry : products.entrySet()) {
      String key = entry.getKey();
      Product value = entry.getValue();
      if (value.getPrice() < price) {
        productsUnderPrice.put(key, value);
      }
    }

    return Collections.unmodifiableCollection(productsUnderPrice.values());
  }


  /**
   * Allows/Inhibits client's notifications of this product
   *
   * @param clientId  client's key
   * @param productId product's key
   *
   * @throws InvalidClientKeyException  client's key doesn't exist
   * @throws InvalidProductKeyException product's key doesn't exist
   */
  public void toggleProductNotifications(String clientId, String productId) throws InvalidClientKeyException,
      InvalidProductKeyException {

    /* We can't toggle notifications if the client's doesn't exist*/
    if (!checkIfClientExists(clientId)) throw new InvalidClientKeyException(clientId);

    /* We can't toggle notifications if the product's doesn't exist*/
    if (!checkIfProductExists(productId)) throw new InvalidProductKeyException(productId);

    /* Gets client */
    Client client = getClient(clientId);

    getProduct(productId).toggleNotifications(clientId, client);

  }

  /**
   * Activate/Deactivate supplier's activity
   *
   * @param id supplier's id
   * @throws InvalidSupplierKeyException supplier's key doesn't exist
   */
  public boolean toggleTransactions(String id) throws InvalidSupplierKeyException {

    /* We can't register a supplier that already exist */
    if (!checkIfSupplierExists(id)) throw new InvalidSupplierKeyException(id);

    /* Changes state and returns new state */
    return getSupplier(id).toggleStatus();
  }

}
