package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.InvalidSupplierKeyException;


/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  /** Supplier's unique ID */
  private Input<String> id;


  /**
   * DoShowSupplierTransactions class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    this.id = _form.addStringInput(Message.requestSupplierKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {

      /* Gets supplier id and displays all past orders */
      _form.parse();
      _display.popup(_receiver.getSupplierHistoryAsString(id.value()));

      /* If supplier key doesn't exist, we throw an error */
    } catch (InvalidSupplierKeyException e) {
      throw new UnknownSupplierKeyException(id.value());
    }

  }

}
