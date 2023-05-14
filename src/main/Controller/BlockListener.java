package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class BlockListener extends MouseAdapter {
    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;

    public BlockListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiModel.setSelectedPiece(e.getComponent().getLocation());
        klotskiUI.selectBlock(e.getComponent());
    }
}
