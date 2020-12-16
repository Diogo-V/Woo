package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.exceptions.InvalidTransactionKeyException;


/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  /** Id of the sale that is being paid */
  private Input<Integer> saleId;


  /**
   * DoPay class constructor.
   *
   * @param receiver Storefront
   * */
  public DoPay(Storefront receiver) {
    super(Label.PAY, receiver);
    this.saleId = _form.addIntegerInput(Message.requestTransactionKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Gets sale id that is going to be paid and does so */
      _form.parse();
      _receiver.pay(saleId.value());

      /* Throws error when transaction key does not exist */
    } catch (InvalidTransactionKeyException e) {
      throw new UnknownTransactionKeyException(saleId.value());
    }

  }

}
