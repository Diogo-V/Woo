package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.InvalidProductKeyException;


/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  /** Requested product's id */
  private Input<String> id;

  /** New price */
  private Input<Integer> price;

  /**
   * DoChangePrice class constructor.
   *
   * @param receiver Storefront
   * */
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    this.id = _form.addStringInput(Message.requestProductKey());
    this.price = _form.addIntegerInput(Message.requestPrice());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {
      /* Request parse */
      _form.parse();

      /* Change product's price */
      _receiver.changePrice(id.value(), price.value());

      /* Informs the user that the key doesn't exist */
    } catch (InvalidProductKeyException e){
      throw new UnknownProductKeyException(e.getKey());
    }

  }

}
