package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnavailableProductException;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.InvalidClientKeyException;
import woo.exceptions.InvalidProductAmountException;
import woo.exceptions.InvalidProductKeyException;


/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

  /** Client's id that is buying this product */
  private Input<String> clientId;

  /** Product's id that is being sold */
  private Input<String> productId;

  /** Deadline (date until when this sale has to be payed) */
  private Input<Integer> deadline;

  /** Amount of this product tha is being bought */
  private Input<Integer> amount;


  /**
   * DoRegisterSaleTransaction class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    this.clientId = _form.addStringInput(Message.requestClientKey());
    this.deadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    this.productId = _form.addStringInput(Message.requestProductKey());
    this.amount = _form.addIntegerInput(Message.requestAmount());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Gets inputs from user and creates a Sale */
      _form.parse();
      _receiver.registerSale(clientId.value(), deadline.value(), productId.value(), amount.value());

    /* Throws error when client key is invalid */
    } catch (InvalidClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());

    /* Throws error when requested amount is bigger than the available one */
    } catch (InvalidProductAmountException e) {
      throw new UnavailableProductException(productId.value(), amount.value(), e.getAvailable());

    /* Throws error when product key is not valid */
    } catch (InvalidProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }

  }

}
