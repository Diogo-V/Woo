package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.InvalidClientKeyException;


/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {

  /** Holds client's id */
  private Input<String> id;


  /**
   * DoShowClient class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowClient(Storefront receiver) {
    super(Label.SHOW_CLIENT, receiver);
    this.id = _form.addStringInput(Message.requestClientKey());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Display client */
      _display.addLine(_receiver.showClient(id.value()));

      /* Display client's notifications */
      _display.addLine(_receiver.showClientNotifications(id.value()));

      /* Displays info */
      _display.display();

      /* Informs the user that we this key is invalid */
    } catch (InvalidClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }

  }

}