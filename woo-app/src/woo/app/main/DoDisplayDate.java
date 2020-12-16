package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;


/**
 * Show current date.
 */
public class DoDisplayDate extends Command<Storefront> {


  /**
   * DoAdvanceDate class constructor.
   *
   * @param receiver Storefront
   * */
  public DoDisplayDate(Storefront receiver) {
    super(Label.SHOW_DATE, receiver);
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

      /* Request parse */
      _form.parse();

      /* Gets current date and displays it to the user */
      _display.popup(Message.currentDate(_receiver.getCurrentDate()));

  } 

}
