package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles actions related to open the saved game list in the Klotski game.
 */
class SavedGamesListListener extends UIController {

    final SaveCommand save;

    SavedGamesListListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI, SaveCommand save) {
        super(klotskiModel, klotskiUI);
        this.save = save;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            klotskiUI.showSavedGames(klotskiModel.getSavedGameList(), new SavedGamesListener(klotskiModel, klotskiUI, save));
        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }
    }

}


