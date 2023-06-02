package Controller;


import Model.KlotskiModel;
import Model.Move;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that retrieve the nextBestMove and displayed it
 */
class NextCommand extends UIController {

    NextCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            Move bestMove = klotskiModel.nextBestMove();
            klotskiUI.makeMove(bestMove, klotskiModel.getCounter());
            winHandler();
        } catch (Exception ex) {
            klotskiUI.showMessage("Connectivity problems using solver, retry later", "Solver", JOptionPane.ERROR_MESSAGE);
        }
    }

}