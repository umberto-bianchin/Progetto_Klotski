package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles authentication actions (logging in and signing up) in the Klotski game.
 */
public class AuthListener extends UIController{
    
    public AuthListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        String username = ((JButton) e.getSource()).getClientProperty("username").toString();
        String password = ((JButton) e.getSource()).getClientProperty("password").toString();
        String type = ((JButton) e.getSource()).getText();  // Log in or Sign up

        try {

            if (type.equals("Log in")) {
                klotskiModel.login(username, password);
            } else if (type.equals("Sign up")) {
                klotskiModel.registration(username, password);
            } else {return;}

            klotskiUI.showMessage("Hi " + username + "! " + type + " successfully", type, JOptionPane.INFORMATION_MESSAGE);
            klotskiUI.initUser(username);

        }  catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), type, JOptionPane.ERROR_MESSAGE);
        }

    }

}
