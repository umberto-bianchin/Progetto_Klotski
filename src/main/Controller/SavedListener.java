package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SavedListener implements ActionListener {

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;
    private final Controller controller;

    SavedListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI, Controller controller) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            klotskiUI.showSavedGames(klotskiModel.getSavedGameList(), new SelectSavedGamesListener(klotskiModel, klotskiUI, controller));
        } catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }
    }
}


