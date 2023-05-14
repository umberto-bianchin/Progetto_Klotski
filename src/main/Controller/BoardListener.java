package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class BoardListener extends MouseAdapter {


    private final KlotskiModel klotskiModel;
    private final KlotskiUI klotskiUI;
    private final Controller controller;

    BoardListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI, Controller controller) {
        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
        this.controller = controller;

    }

    @Override
    public void mousePressed(MouseEvent e) {

        try {
            Rectangle finalPosition = klotskiModel.moveSelectedPiece(e.getPoint());
            klotskiUI.moveSelectedBlock(finalPosition, klotskiModel.getCounter());
            controller.winHandler();
        }
        catch (RuntimeException ignored){}

    }
}




