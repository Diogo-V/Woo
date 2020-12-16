package woo.app.products;

import pt.tecnico.po.ui.Command;  
import woo.Storefront;
import pt.tecnico.po.ui.DialogException;
import woo.products.Product;
import java.util.Collection;


/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<Storefront> {


  /**
   * DoShowAllProducts class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowAllProducts(Storefront receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    /* Gets all the products of this store */
    Collection<Product> products = _receiver.getAllProducts();

    /* Accumulates all the info that is going to be displayed */
    products.forEach((product) -> _display.addLine(product.toString()));

    /* Displays info */
    _display.display();

  }

}
