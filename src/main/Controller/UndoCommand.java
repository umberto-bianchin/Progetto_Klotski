package Controller;

import Model.Model;
import View.View;
import Model.Move;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class UndoCommand implements ActionListener{

    private final Model model;
    private final View view;

    UndoCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            Move lastMoveRev = model.undo();
            view.makeMove(lastMoveRev, model.getCounter());
        }
        catch (RuntimeException ignored){}
}
}
