package woo.app.main;

import pt.tecnico.po.ui.Command; 
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;    
import woo.Storefront;
import woo.app.exceptions.InvalidDateException;
import woo.exceptions.DateException;


/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {
  
  /** Days to advance */
  private Input<Integer> days;


  /**
   * DoAdvanceDate class constructor.
   *
   * @param receiver Storefront
   * */
  public DoAdvanceDate(Storefront receiver) {
    super(Label.ADVANCE_DATE, receiver);
    this.days = _form.addIntegerInput(Message.requestDaysToAdvance());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Request parse */
      _form.parse();

      /* Advance date*/
      _receiver.advanceDate(days.value());

      /* Informs the user that the date is invalid */
    } catch (DateException e) {
      throw new InvalidDateException(days.value());
    }
    
  }
 
}

  