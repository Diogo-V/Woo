package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.InvalidSupplierKeyException;


/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  /** Supplier's unique ID */
  private Input<String> id;


  /**
   * DoToggleTransactions class constructor.
   *
   * @param receiver Storefront
   * */
  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    this.id = _form.addStringInput(Message.requestSupplierKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {
      /* Request parse */
      _form.parse();

      /* Allows/Inhibits supplier's activity */
      _display.popup(_receiver.toggleTransactions(id.value()) ? Message.transactionsOn(id.value()) :
          Message.transactionsOff(id.value()));

      /* Informs the user that the key is invalid*/
    } catch(InvalidSupplierKeyException e){
      throw new UnknownSupplierKeyException(id.value());
    }

  }

}
