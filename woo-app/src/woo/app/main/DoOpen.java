package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.FileOpenFailedException;
import woo.exceptions.UnavailableFileException;
import java.io.IOException;


/**
 * Open existing saved state.
 */
public class DoOpen extends Command<Storefront> {

  /** File name that is going to be loaded */
  private Input<String> file;


  /**
   * DoOpen class constructor.
   *
   * @param receiver Storefront
   * */
  public DoOpen(Storefront receiver) {
    super(Label.OPEN, receiver);
    this.file = _form.addStringInput(Message.openFile());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    try {

      /* Loads file */
      _form.parse();
      _receiver.load(file.value());

      /* Informs the user that the file is not available */
    } catch (UnavailableFileException e) {
      throw new FileOpenFailedException(e.getFilename());

      /* Informs the user that a valid input class was not found */
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }

  }

}
