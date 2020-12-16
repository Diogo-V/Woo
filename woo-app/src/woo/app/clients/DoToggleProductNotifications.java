package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.InvalidClientKeyException;
import woo.exceptions.InvalidProductKeyException;


/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  /** Client's id whose notifications are to be updated */
  private Input<String> clientId;

  /** Product whose notifications are going to be updated */
  private Input<String> productId;


  /**
   * DoToggleProductNotifications class constructor.
   *
   * @param receiver Storefront
   * */
  public DoToggleProductNotifications(Storefront receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    this.clientId = _form.addStringInput(Message.requestClientKey());
    this.productId = _form.addStringInput(Message.requestProductKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Allows/Inhibits client's notifications of this product */
      _receiver.toogleProductNotifications(clientId.value(), productId.value());

      /* Informs the user that the client's key is invalid*/
    } catch (InvalidClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());

      /* Informs the user that the product's key is invalid*/
    } catch (InvalidProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }

  }

}
