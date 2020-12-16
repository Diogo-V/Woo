package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateKeyException;
import woo.exceptions.InvalidSupplierKeyException;


/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  /** Books's id */
  private Input<String> productId;

  /** Book title */
  private Input<String> title;

  /** Book author */
  private Input<String> author;

  /** Book ISBN */
  private Input<String> isbn;

  /** Book's price */
  private Input<Integer> price;

  /** Book's critical value */
  private Input<Integer> critical;

  /** Supplier's id */
  private Input<String> supplierId;


  /**
   * DoRegisterProductBook class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    this.productId = _form.addStringInput(Message.requestProductKey());
    this.title = _form.addStringInput(Message.requestBookTitle());
    this.author = _form.addStringInput(Message.requestBookAuthor());
    this.isbn = _form.addStringInput(Message.requestISBN());
    this.price = _form.addIntegerInput(Message.requestPrice());
    this.critical = _form.addIntegerInput(Message.requestStockCriticalValue());
    this.supplierId = _form.addStringInput(Message.requestSupplierKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Request parse*/
      _form.parse();

      /* Register new book*/
      _receiver.registerBook(productId.value(), title.value(), author.value(), isbn.value(),
              supplierId.value(), price.value(), critical.value());

      /* Informs the user that the key already exist */
    } catch (DuplicateKeyException e) {
      throw new DuplicateProductKeyException(e.getKey());

      /* Informs the user that the supplier doesn't exist */
    } catch (InvalidSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }

  }

}
