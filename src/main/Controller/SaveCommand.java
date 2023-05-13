package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SaveCommand implements ActionListener {

    private final Model model;
    private final View view;
    private String name = "";

    SaveCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() != this)
            name = view.askGameName();

        try {
            model.saveGame(name);
            view.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch(IllegalAccessException ex){

            if(view.showAuthenticationDialog()) {
                e.setSource(this);
                actionPerformed(e);
            }
        } catch(IllegalArgumentException ex) {
            view.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            e.setSource(null);
            actionPerformed(e);
        } catch (Exception ex) {
            view.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
        }

    }
}