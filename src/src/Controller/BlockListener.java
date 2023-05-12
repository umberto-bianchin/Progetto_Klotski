package Controller;

import Model.Model;
import View.View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class BlockListener extends MouseAdapter {
    private final Model model;
    private final View view;

    public BlockListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.setSelectedPiece(e.getComponent().getLocation());
        view.selectBlock(e.getComponent());
    }
}
