package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


class SavedListener implements ActionListener {

    private final Model model;
    private final View view;
    private final Controller controller;

    SavedListener(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            view.showSavedGames(model.getSavedGameList(), new SelectSavedGamesListener(model, view, controller));
        } catch (SQLException ex) {
            view.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
        }
    }
}


