package Controller;

import Model.Model;
import View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HomeCommand implements ActionListener {

    private final Model model;
    private final View view;

    HomeCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.restartState();
        view.initStart();
    }

}
