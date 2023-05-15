package Controller;

import Model.KlotskiModel;
import Model.Move;
import View.KlotskiUI;

import java.awt.event.MouseEvent;

class BoardListener extends UIController {


    BoardListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        try {
            Move move = klotskiModel.moveSelectedPiece(e.getPoint());
            klotskiUI.makeMove(move, klotskiModel.getCounter());
            winHandler();
        }
        catch (RuntimeException ignored){}

    }
}




