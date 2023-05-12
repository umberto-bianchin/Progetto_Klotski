package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SaveCommand implements ActionListener {

    private final Model model;
    private final View view;

    SaveCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = view.askGameName();

        try {
            model.saveGame(name);
            view.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            view.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
        }

    }
}