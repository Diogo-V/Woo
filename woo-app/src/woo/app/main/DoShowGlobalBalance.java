package woo.app.main;

import pt.tecnico.po.ui.Command;
import woo.Storefront;


/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<Storefront> {


  /**
   * DoShowGlobalBalance class constructor.
   *
   * @param receiver Storefront
   * */
  public DoShowGlobalBalance(Storefront receiver) { super(Label.SHOW_BALANCE, receiver); }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

    /* Get available balance (difference between all payed sales and orders) */
    int available = _receiver.getAvailableBalance();

    /* Get store's accounting balance (difference between all sales with discount/penalty and orders) */
    int accounting = _receiver.getAccountingBalance();

    /* Displays the current balance to the user */
    _display.popup(Message.currentBalance(available, accounting));

  }

}
