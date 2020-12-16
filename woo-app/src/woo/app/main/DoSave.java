package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.FileOpenFailedException;
import woo.exceptions.MissingFileAssociationException;
import woo.exceptions.UnavailableFileException;
import java.io.IOException;


/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {

  /** File name that is going to be loaded */
  private Input<String> file;


  /**
   * DoSave class constructor.
   *
   * @param receiver Storefront
   * */
  public DoSave(Storefront receiver) {
    super(Label.SAVE, receiver);
    if ( ! _receiver.checkIfHasFileAssociated()) { this.file = _form.addStringInput(Message.newSaveAs()); }
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  public final void execute() throws FileOpenFailedException {

    try {

      /* Saves new file */
      if ( ! _receiver.checkIfHasFileAssociated()) {

        _form.parse();
        _receiver.saveAs(file.value());

      /* Saves file */
      } else {
        _receiver.save();
      }

      /* Informs the user that we couldn't access the file */
    } catch (UnavailableFileException e) {
      throw new FileOpenFailedException(e.getFilename());

      /* Informs the user that the file was not associated */
    } catch (MissingFileAssociationException | IOException e) {
      e.printStackTrace();
    }

  }

}
