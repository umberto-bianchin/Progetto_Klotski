package Controller;


import Model.Model;
import Model.Move;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NextCommand implements ActionListener {

    private final Model model;
    private final View view;
    private final Controller controller;

    NextCommand(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Move bestMove = model.nextBestMove();
            view.makeMove(bestMove, model.getCounter());
            controller.WinHandler();
        } catch (Exception ex) {
            view.showMessage("Connectivity problems using solver, retry later", "Solver", JOptionPane.ERROR_MESSAGE);
        }
    }
}