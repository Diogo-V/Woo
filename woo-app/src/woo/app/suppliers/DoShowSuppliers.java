package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;
import woo.suppliers.Supplier;
import java.util.Collection;


/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<Storefront> {


  /**
   * DoShowSuppliers class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowSuppliers(Storefront receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    /* Gets all the suppliers of this store */
    Collection<Supplier> suppliers = _receiver.getAllSuppliers();

    /* Accumulates all the info that is going to be displayed */
    suppliers.forEach((supplier) ->
            _display.addLine(supplier.toString() + (supplier.getStatus() ? Message.yes() : Message.no())));

    /* Displays info */
    _display.display();

  }

}
