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

        } else if (name.equals("delUser")) {

            try {
                //delete user and logout
                klotskiModel.delUser();
                klotskiUI.logout();
            } catch (Exception ex) {
                klotskiUI.showMessage(ex.getMessage(), "Authentication", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
