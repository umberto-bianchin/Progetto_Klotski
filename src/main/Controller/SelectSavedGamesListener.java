package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SelectSavedGamesListener implements ActionListener{

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;
    private final Controller controller;

    SelectSavedGamesListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI, Controller controller) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            if (((JButton) e.getSource()).getName().startsWith("game")) {

                klotskiModel.resumeState(((JButton) e.getSource()).getName().substring(4));
                controller.initGameView(klotskiModel.getCurrentPositions(), klotskiModel.getCounter());

            } else if (((JButton) e.getSource()).getName().startsWith("delete")) {

                klotskiModel.deleteSavedGame(((JButton) e.getSource()).getName().substring(6));
                klotskiUI.showSavedGames(klotskiModel.getSavedGameList(), this);
            }

        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }
    }
}





