package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles the logout of a user
 */
class DisconnectionListener extends UIController{

    DisconnectionListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        String name = ((JButton) e.getSource()).getName();

        if(name.equals("logOut")) {

            klotskiModel.logout();
            klotskiUI.logout();

        } else if (name.equals("delUser")){

            try {
                klotskiModel.delUser(); //delete user and logout
                klotskiUI.logout();
            } catch (Exception ex) {
                klotskiUI.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
