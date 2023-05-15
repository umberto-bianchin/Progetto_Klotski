package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

class SaveCommand extends UIController {

    String name = "";

    SaveCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() != this)
            name = klotskiUI.askGameName();

        if(name == null) // when the askName windows is closed with "X"
            return;

        try {

            klotskiModel.saveGame(name);
            klotskiUI.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);

        } catch(IllegalAccessException ex){

            if(klotskiUI.showAuthenticationDialog()) {
                e.setSource(this);
                mousePressed(e);
            }
        } catch(IllegalArgumentException ex) {

            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            e.setSource(null);
            mousePressed(e);

        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
        }
    }

}