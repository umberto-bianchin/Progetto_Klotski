package Controller;


import Model.KlotskiModel;
import Model.Move;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NextCommand implements ActionListener {

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;
    private final Controller controller;

    NextCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI, Controller controller) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Move bestMove = klotskiModel.nextBestMove();
            klotskiUI.makeMove(bestMove, klotskiModel.getCounter());
            controller.winHandler();
        } catch (Exception ex) {
            klotskiUI.showMessage("Connectivity problems using solver, retry later", "Solver", JOptionPane.ERROR_MESSAGE);
        }
    }
}