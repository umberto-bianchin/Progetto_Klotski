package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

class SelectSavedGamesListener extends UIController {

    SelectSavedGamesListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    public void mousePressed(MouseEvent e) {

        try {

            if (((JButton) e.getSource()).getName().startsWith("game")) {

                klotskiModel.resumeState(((JButton) e.getSource()).getName().substring(4));
                klotskiUI.initGame(klotskiModel.getCurrentPositions(), klotskiModel.getCounter());

            } else if (((JButton) e.getSource()).getName().startsWith("delete")) {

                klotskiModel.deleteSavedGame(((JButton) e.getSource()).getName().substring(6));
                klotskiUI.showSavedGames(klotskiModel.getSavedGameList(), this);
            }

        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }

    }

}





