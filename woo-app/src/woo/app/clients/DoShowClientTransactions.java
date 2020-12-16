package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.InvalidClientKeyException;
import woo.transactions.Sale;
import java.util.Collection;


/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  /** Holds client's id */
  private Input<String> id;


  /**
   * DoShowDoShowClientTransactionsClient class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowClientTransactions(Storefront receiver) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, receiver);
    this.id = _form.addStringInput(Message.requestClientKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {
    try {

      /* Request parse */
      _form.parse();

      /* Gets all the clients of this store */
      Collection<Sale> sales  = _receiver.getAllClientTransactions(id.value());

      /* Accumulates all the info that is going to be displayed */
      sales.forEach((sale) -> _display.addLine(sale.toString()));

      /* Displays info */
      _display.display();

      /* Informs the user that we this key is invalid */
    } catch (InvalidClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }

  }

}
