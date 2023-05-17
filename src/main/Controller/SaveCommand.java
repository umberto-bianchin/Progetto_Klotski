package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * The class is a UI controller that handles the process of saving games
 */
class SaveCommand extends UIController {

    String name = null;
    boolean resumed = false;

    SaveCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        //not ask always the new game name, because it could already know (if game resumed or the name was already typed but a minor exception occurred)
        if (name == null )
            name = klotskiUI.askGameName();

        if (name == null) // when the askName windows is closed with "X"
            return;

        try {
            klotskiModel.saveGame(name, resumed);
            klotskiUI.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);
            //meanwhile the user is playing inside this game every saving will use the same name already typed
            resumed = true;

        } catch (IllegalAccessException ex) { // when the player isn't authenticated

            if (klotskiUI.showAuthenticationDialog()) { //ask if the player want to authenticate if true:
                mousePressed(e); // the name will not be re-ask because name != null
            }
        } catch (IllegalArgumentException ex) {  //blank names or more than one

            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            setName(null); //re-ask the game name
            mousePressed(e); // re-ask how to save the game

        } catch (SQLException ex) {
            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param name current name of the game, null if the user isn't playing (home screen)
     */
    public void setName(String name) {
        this.name = name;
        resumed = name != null;
    }

}