package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.WrongSupplierException;
import woo.exceptions.InvalidProductKeyException;
import woo.exceptions.InvalidSupplierKeyException;
import woo.exceptions.ProductNotSuppliedException;
import woo.exceptions.SupplierNotAuthorizedException;
import java.util.ArrayList;


/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  /** Supplier's id */
  private Input<String> supplierId;

  /** Product's id that is being bought */
  private Input<String> productId;

  /** Amount that is being bought */
  private Input<Integer> amount;

  /** Checks if user wants to add more products */
  private Input<Boolean> requestMore;


  /**
   * DoRegisterOrderTransaction class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
    this.supplierId = _form.addStringInput(Message.requestSupplierKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Holds product + amount that is going to be sent to woo-core */
      ArrayList<String> requestedProductIds = new ArrayList<>();
      ArrayList<Integer> requestedProductAmount = new ArrayList<>();

      /* Parses form and gets input supplier id */
      _form.parse();
      String sId = supplierId.value();

      /* Since we need to ask multiple times for a product, amount and if the user wants to add more. We create a new
      *  form for that matter */
      Form loopForm = new Form();
      productId = loopForm.addStringInput(Message.requestProductKey());
      amount = loopForm.addIntegerInput(Message.requestAmount());
      requestMore = loopForm.addBooleanInput(Message.requestMore());

      /* Gets all the input products from the user */
      do {
        loopForm.parse();
        requestedProductIds.add(productId.value());
        requestedProductAmount.add(amount.value());
      } while (requestMore.value());

      /* Sends everything to our store for processing */
      _receiver.registerOrder(sId, requestedProductIds, requestedProductAmount);

      /* Throws error when supplier key is not valid */
    } catch (InvalidSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());

      /* Throws error when product key is not valid */
    } catch (InvalidProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());

      /* Throws error when this supplier can not receive orders */
    } catch (SupplierNotAuthorizedException e) {
      throw new UnauthorizedSupplierException(e.getSupplierKey());

      /* Throws error when product is not from this supplier */
    } catch (ProductNotSuppliedException e) {
      throw new WrongSupplierException(e.getSupplierKey(), e.getProductKey());
    }

  }

}
