package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.InvalidClientKeyException;
import woo.transactions.Sale;
import java.util.Collection;


/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  /** Client's id */
  private Input<String> id;


  /**
   * DoLookupPaymentsByClient class constructor.
   *
   * @param receiver Storefront
   * */
  public DoLookupPaymentsByClient(Storefront receiver) {
    super(Label.PAID_BY_CLIENT, receiver);
    this.id = _form.addStringInput(Message.requestClientKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Gets all the paid sales of this client */
      Collection<Sale> sales = _receiver.lookupPaymentsByClient(id.value());

      /* Accumulates all the info that is going to be displayed */
      sales.forEach((sale) -> _display.addLine(sale.toString()));

      /* Displays info */
      _display.display();

      /* Informs the user that the client's key is invalid*/
    } catch (InvalidClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }

  }

}
