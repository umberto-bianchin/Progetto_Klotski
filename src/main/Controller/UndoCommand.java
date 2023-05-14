package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import Model.Move;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class UndoCommand implements ActionListener{

    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;

    UndoCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            Move lastMoveRev = klotskiModel.undo();
            klotskiUI.makeMove(lastMoveRev, klotskiModel.getCounter());
        }
        catch (RuntimeException ignored){}
}
}
