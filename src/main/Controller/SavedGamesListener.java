package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles the opening and deletion of saved games in the Klotski game.
 */
class SavedGamesListener extends UIController {

    SavedGamesListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    public void mousePressed(MouseEvent e) {

        try {

            String name = ((JButton) e.getSource()).getName();

            if (name.startsWith("game")) {

                klotskiModel.resumeState(name.substring(4)); //name is in the form game14
                klotskiUI.initGame(klotskiModel.getCurrentPositions(), klotskiModel.getCounter());

            } else if (name.startsWith("delete")) {

                klotskiModel.deleteSavedGame(name.substring(6)); // name is in the form delete11
                klotskiUI.showSavedGames(klotskiModel.getSavedGameList(), this); //reopen the updated page
            }

        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }

    }

}





