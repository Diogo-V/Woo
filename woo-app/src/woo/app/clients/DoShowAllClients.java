package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;
import woo.clients.Client;
import java.util.Collection;


/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<Storefront> {


  /**
   * DoShowAllClients class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowAllClients(Storefront receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public void execute() throws DialogException {

    /* Gets all the clients of this store */
    Collection<Client> clients  = _receiver.getAllClients();

    /* Accumulates all the info that is going to be displayed */
    clients.forEach((client) -> _display.addLine(client.toString()));

    /* Displays info */
    _display.display();

  }

}

