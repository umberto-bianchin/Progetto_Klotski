package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SaveCommand implements ActionListener {

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;
    private String name = "";

    SaveCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
                actionPerformed(e);
            }
        } catch(IllegalArgumentException ex) {

            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            e.setSource(null);
            actionPerformed(e);

        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
        }

    }
}