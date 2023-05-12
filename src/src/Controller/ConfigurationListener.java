package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


class ConfigurationListener  implements ActionListener {

    private final Model model;
    private final View view;
    private final Controller controller;

    ConfigurationListener(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            int num_config = Integer.parseInt(((JButton) e.getSource()).getName());
            model.initState(num_config);
            controller.initGameView(model.getInitialPositions(), 0);
        }
        catch (SQLException ex){
            view.showMessage(ex.getMessage(), "Game Selector", JOptionPane.ERROR_MESSAGE);
        }
    }
}
