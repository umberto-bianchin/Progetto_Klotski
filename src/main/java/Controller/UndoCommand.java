package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import Model.Move;

import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles the Undo operation in the Klotski game.
 */
class UndoCommand extends UIController{

    UndoCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            Move lastMoveRev = klotskiModel.undo();
            klotskiUI.makeMove(lastMoveRev, klotskiModel.getCounter());
        }
        catch (RuntimeException ignored){}
    }

}
