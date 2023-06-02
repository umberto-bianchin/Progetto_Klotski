package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * The class is a UI controller that handles the choice of initial configuration in the Klotski game
 */
class ConfigurationListener extends UIController {

    ConfigurationListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            int num_config = Integer.parseInt(((JButton) e.getSource()).getName());  // 0-3
            klotskiModel.initState(num_config);
            klotskiUI.initGame(klotskiModel.getCurrentPositions(), klotskiModel.getCounter());
        }
        catch (SQLException | IllegalArgumentException ex){
            klotskiUI.showMessage(ex.getMessage(), "Game Selector", JOptionPane.ERROR_MESSAGE);
        }
    }

}
