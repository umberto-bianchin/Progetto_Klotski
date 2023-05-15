package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


class ConfigurationListener extends UIController {

    ConfigurationListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            int num_config = Integer.parseInt(((JButton) e.getSource()).getName());
            klotskiModel.initState(num_config);
            klotskiUI.initGame(klotskiModel.getCurrentPositions(), klotskiModel.getCounter());
        }
        catch (SQLException ex){
            klotskiUI.showMessage(ex.getMessage(), "Game Selector", JOptionPane.ERROR_MESSAGE);
        }
    }

}
