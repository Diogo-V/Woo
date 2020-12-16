package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateKeyException;
import woo.exceptions.InvalidServiceTypeException;
import woo.exceptions.InvalidSupplierKeyException;


/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {

  /** Product's id */
  private Input<String> productId;

  /** Product's price */
  private Input<Integer> price;

  /** Product's critical value */
  private Input<Integer> critical;

  /** Supplier's id */
  private Input<String> supplierId;

  /** Requested service type */
  private Input<String> service;


  /**
   * DoRegisterProductBox class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    this.productId = _form.addStringInput(Message.requestProductKey());
    this.price = _form.addIntegerInput(Message.requestPrice());
    this.critical = _form.addIntegerInput(Message.requestStockCriticalValue());
    this.supplierId = _form.addStringInput(Message.requestSupplierKey());
    this.service = _form.addStringInput(Message.requestServiceType());

  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Register a new box*/
      _receiver.registerBox(productId.value(), service.value(), supplierId.value(), price.value(), critical.value());

    /* Informs the user that the key already exist */
    } catch (DuplicateKeyException e) {
      throw new DuplicateProductKeyException(e.getKey());

    /* Informs the user that the supplier's key doesn't exist */
    } catch (InvalidSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());

    /* Informs the user that the service type doesn't exist */
    } catch (InvalidServiceTypeException e) {
      throw new UnknownServiceTypeException(service.value());
    }

  }

}
