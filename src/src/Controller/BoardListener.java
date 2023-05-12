package Controller;

import Model.Model;
import View.View;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class BoardListener extends MouseAdapter {


    private final Model model;
    private final View view;
    private final Controller controller;

    BoardListener(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;

    }

    @Override
    public void mousePressed(MouseEvent e) {

        try {
            Rectangle finalPosition = model.moveSelectedPiece(e.getPoint());
            view.moveSelectedBlock(finalPosition, model.getCounter());
            controller.WinHandler();
        }
        catch (RuntimeException ignored){}

    }
}




