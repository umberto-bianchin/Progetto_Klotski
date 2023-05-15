package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles actions related to open the saved game list in the Klotski game.
 */
class SavedGamesListListener extends UIController {

    SavedGamesListListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            klotskiUI.showSavedGames(klotskiModel.getSavedGameList(), new SavedGamesListener(klotskiModel, klotskiUI));
        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }
    }

}


