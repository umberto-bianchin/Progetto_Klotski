package Controller;

import Model.Model;
import Model.Piece;
import View.Block;
import View.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class Controller {

    private final View view;
    private final Model model;

    public Controller(View view, Model model) throws IOException {

        this.view = view;
        this.model = model;

        view.setPositionBlocks(model.getState().getInitial_config());

        for (Block piece : view.getBlocksRepresentation()) {
            piece.addListener(new BlockListener(piece));
        }

        view.addBoardListener(new BoardListener());
        view.setDisplayedCounter(model.getState().getCounter());

        ActionListener[] actionListeners = {new RestartCommand(), new SaveCommand(), new NextCommand(), new UndoCommand()};

        view.getButtons().addCommand(actionListeners);

    }

    private void move(MouseEvent e) {

        Piece selectedPiece = model.getState().getSelectedPiece();

        if (selectedPiece != null) {

            Point click = new Point(e.getX(), e.getY());
            Rectangle possiblePosition = selectedPiece.checkAvailable(click);
            Rectangle window = new Rectangle(0, 0, 400, 500);

            if (possiblePosition == null || !window.contains(possiblePosition)) {
                return;
            }

            for (Piece piece : model.getState().getCurrent_config()) {
                if (piece != selectedPiece && piece.intersection(possiblePosition)) {
                    return;
                }
            }

            boolean win = selectedPiece.move(possiblePosition);
            view.moveSelectedBlock(possiblePosition);

            if (win) {
                view.winMessage();
            }

            view.selectBlock(null);
            model.getState().setSelectedPiece(null);

            model.getState().incrementCounter();
            view.setDisplayedCounter(model.getState().getCounter());
        }
    }


    class BoardListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            move(e);
        }

    }

    class BlockListener extends MouseAdapter {

        private final Block blockControlled;
        private Piece pieceControlled;

        public BlockListener(Block block) {
            blockControlled = block;

            for (Piece piece : model.getState().getCurrent_config()) {
                if (piece.getPosition().contains(blockControlled.getBounds())) {
                    this.pieceControlled = piece;
                    return;
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            model.getState().setSelectedPiece(pieceControlled);
            view.selectBlock(blockControlled);
        }

    }

    class NextCommand implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    class RestartCommand implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                view.setPositionBlocks(model.getState().getInitial_config());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            model.getState().setCurrent_config(model.getState().getInitial_config());

            model.getState().setSelectedPiece(null);
            view.selectBlock(null);

            model.getState().resetCounter();
            view.setDisplayedCounter(model.getState().getCounter());

        }
    }

    class SaveCommand implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class UndoCommand implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}



