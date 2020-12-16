package woo.app.clients;

import pt.tecnico.po.ui.Command;    
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input; 
import woo.app.exceptions.DuplicateClientKeyException;
import woo.Storefront;
import woo.exceptions.DuplicateKeyException;


/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront> {

  /** Client's unique ID */
  private Input<String> id;

  /** Client's name */
  private Input<String> name;

  /** Client's address */
  private Input<String> address;


  /**
   * DoRegisterClient class constructor.
   *
   * @param receiver Storefront
   * */
  public DoRegisterClient(Storefront receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    this.id = _form.addStringInput(Message.requestClientKey());
    this.name = _form.addStringInput(Message.requestClientName());
    this.address = _form.addStringInput(Message.requestClientAddress());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Register new client */
      _receiver.registerClient(id.value(), name.value(), address.value());

    /* Informs the user that the key already exist */
    } catch (DuplicateKeyException e) {
      throw new DuplicateClientKeyException(e.getKey());
    }

  }

}
