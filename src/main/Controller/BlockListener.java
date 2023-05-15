package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.event.MouseEvent;


class BlockListener extends UIController {

    public BlockListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiModel.setSelectedPiece(e.getComponent().getLocation());
        klotskiUI.selectBlock(e.getComponent());
    }
}
