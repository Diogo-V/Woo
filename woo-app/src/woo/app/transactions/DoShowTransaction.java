package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.exceptions.InvalidTransactionKeyException;


/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<Storefront> {

  /** Transaction's id */
  private Input<Integer> id;


  /**
   * DoShowTransaction class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowTransaction(Storefront receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    this.id = _form.addIntegerInput(Message.requestTransactionKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Gets transaction id and displays it */
      _form.parse();
      _display.popup(_receiver.showTransaction(id.value()));

    /* If this key is not valid, we throw an error */
    } catch (InvalidTransactionKeyException e) {
      throw new UnknownTransactionKeyException(e.getKey());
    }

  }

}
