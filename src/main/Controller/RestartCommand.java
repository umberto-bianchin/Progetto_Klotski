package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RestartCommand implements ActionListener {

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;

    RestartCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        klotskiModel.restartState();
        klotskiUI.restart(klotskiModel.getInitialPositions());
    }
}