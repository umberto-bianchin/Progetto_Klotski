package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles the process of saving games
 */
class SaveCommand extends UIController {

    String name = null;

    SaveCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(name == null && e.getSource() != this) //not ask always the new game name, because it could already know (explained later in the code)
            name = klotskiUI.askGameName();

        if(name == null) // when the askName windows is closed with "X"
            return;

        try {
            klotskiModel.saveGame(name);
            klotskiUI.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);

        } catch(IllegalAccessException ex){ // when the player isn't authenticated

            if(klotskiUI.showAuthenticationDialog()) { //ask if the player want to authenticate if true:
                e.setSource(this); //so the name isn't asked again, but used the one already typed
                mousePressed(e);
            }
        } catch(IllegalArgumentException ex) {  //blank names or more than one

            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            e.setSource(null); // null otherwise it could skip the askGame
            mousePressed(e); // re-ask how to save the game

        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setName(String name){
        this.name = name;
    }

}