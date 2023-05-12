package Controller;

import Model.Model;
import View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LogOutListener implements ActionListener{

    private final Model model;
    private final View view;

    LogOutListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.logout();
        view.logout();
    }

}
