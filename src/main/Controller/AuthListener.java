package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class AuthListener implements ActionListener{

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;

    public AuthListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = ((JButton) e.getSource()).getClientProperty("username").toString();
        String password = ((JButton) e.getSource()).getClientProperty("password").toString();
        String type = ((JButton) e.getSource()).getText();

        try {

            if (type.equals("Log in")) {
                klotskiModel.login(username, password);
            } else if (type.equals("Sign up")) {
                klotskiModel.registration(username, password);
            }

            klotskiUI.showMessage("Hi " + username + "! You have successfully " + type.toLowerCase(Locale.ROOT), type, JOptionPane.INFORMATION_MESSAGE);
            klotskiUI.initUser(username);

        }  catch (Exception ex) {
            klotskiUI.showMessage(ex.getMessage(), type, JOptionPane.ERROR_MESSAGE);
        }

    }

}
