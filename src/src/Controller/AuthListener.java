package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class AuthListener implements ActionListener{

    private final Model model;
    private final View view;

    public AuthListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = ((JButton) e.getSource()).getClientProperty("username").toString();
        String password = ((JButton) e.getSource()).getClientProperty("password").toString();
        String type = ((JButton) e.getSource()).getText();

        try {

            if (type.equals("Log in")) {
                model.login(username, password);
            } else if (type.equals("Sign up")) {
                model.registration(username, password);
            }

            view.showMessage("Hi " + username + "! You have successfully " + type.toLowerCase(Locale.ROOT), type, JOptionPane.INFORMATION_MESSAGE);
            view.initUser(username);

        }  catch (Exception ex) {
            view.showMessage(ex.getMessage(), type, JOptionPane.ERROR_MESSAGE);
        }
    }

}
