package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


class ConfigurationListener  implements ActionListener {

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;
    private final Controller controller;

    ConfigurationListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI, Controller controller) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            int num_config = Integer.parseInt(((JButton) e.getSource()).getName());
            klotskiModel.initState(num_config);
            controller.initGameView(klotskiModel.getCurrentPositions(), 0);
        }
        catch (SQLException ex){
            klotskiUI.showMessage(ex.getMessage(), "Game Selector", JOptionPane.ERROR_MESSAGE);
        }
    }
}
