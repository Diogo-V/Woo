package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.products.Product;
import java.util.Collection;


/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductsUnderTopPrice extends Command<Storefront> {

  /** Upper bound of price that is going to be used as a filter */
  private Input<Integer> price;


  /**
   * DoLookupProductsUnderTopPrice class constructor.
   *
   * @param receiver Storefront
   * */
  public DoLookupProductsUnderTopPrice(Storefront receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    this.price = _form.addIntegerInput(Message.requestPriceLimit());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    /* Request parse */
    _form.parse();

    /* Gets all the products of this store under price limit */
    Collection<Product> products = _receiver.lookupProductsUnderTopPrice(price.value());

    /* Accumulates all the info that is going to be displayed */
    products.forEach((product) -> _display.addLine(product.toString()));

    /* Displays info */
    _display.display();

  }

}
