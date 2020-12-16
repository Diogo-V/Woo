package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateSupplierKeyException;
import woo.exceptions.DuplicateKeyException;


/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {

  /** Supplier's unique ID */
  private Input<String> id;

  /** Supplier's name */
  private Input<String> name;

  /** Supplier's address */
  private Input<String> address;


  /**
   * DoRegisterSupplier class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    this.id = _form.addStringInput(Message.requestSupplierKey());
    this.name = _form.addStringInput(Message.requestSupplierName());
    this.address = _form.addStringInput(Message.requestSupplierAddress());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Register new supplier */
      _receiver.registerSupplier(id.value(), name.value(), address.value());

      /* Informs the user that the key already exits */
    }catch (DuplicateKeyException e){
      throw new DuplicateSupplierKeyException(id.value());
    }

  }

}
