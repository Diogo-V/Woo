package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceLevelException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.DuplicateKeyException;
import woo.exceptions.InvalidServiceQualityException;
import woo.exceptions.InvalidServiceTypeException;
import woo.exceptions.InvalidSupplierKeyException;


/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

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

  /** Requested service quality */
  private Input<String> quality;


  /**
   * DoRegisterProductContainer class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    this.productId = _form.addStringInput(Message.requestProductKey());
    this.price = _form.addIntegerInput(Message.requestPrice());
    this.critical = _form.addIntegerInput(Message.requestStockCriticalValue());
    this.supplierId = _form.addStringInput(Message.requestSupplierKey());
    this.service = _form.addStringInput(Message.requestServiceType());
    this.quality = _form.addStringInput(Message.requestServiceLevel());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Register a new container */
      _receiver.registerContainer(productId.value(), service.value(), quality.value(),
              supplierId.value(), price.value(), critical.value());

      /* Informs the user that the key already exist */
    } catch (DuplicateKeyException e) {
      throw new DuplicateProductKeyException(e.getKey());

      /* Informs the user that the supplier's key doesn't exist */
    } catch (InvalidSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());

      /* Informs the user that the service type doesn't exist */
    } catch (InvalidServiceTypeException e) {
      throw new UnknownServiceTypeException(service.value());

      /* Informs the user that the service quality doesn't exist */
    } catch (InvalidServiceQualityException e) {
      throw new UnknownServiceLevelException(quality.value());
    }

  }

}
