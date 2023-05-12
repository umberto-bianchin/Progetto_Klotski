package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

class SelectSavedGamesListener implements ActionListener{

    private final Model model;
    private final View view;
    private final Controller controller;

    SelectSavedGamesListener(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            if (((JButton) e.getSource()).getName().startsWith("game")) {

                model.resumeState(((JButton) e.getSource()).getName().substring(4));
                controller.initGameView(model.getCurrentPositions(), model.getCounter());

            } else if (((JButton) e.getSource()).getName().startsWith("delete")) {

                model.deleteSavedGame(((JButton) e.getSource()).getName().substring(6));
                view.showSavedGames(model.getSavedGameList(), this);
            }

        } catch (SQLException ex) {
            view.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }
    }
}





