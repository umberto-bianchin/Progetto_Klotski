package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.event.MouseEvent;

class RestartCommand extends UIController {

    RestartCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiModel.restartState();
        klotskiUI.restart(klotskiModel.getCurrentPositions());
    }

}