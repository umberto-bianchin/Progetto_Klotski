package Controller;

import View.*;
import Model.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class Controller {

    private final View view;
    private final Model model;

    public Controller(View view, Model model) throws IOException {

        this.view = view;
        this.model = model;
        view.getBoard().setPiecesRepresentation(model.getState().getInitial_config());

        for (PieceRepresentation piece : view.getBoard().getPiecesRepresentation()) {
            piece.addListener(new PieceListener(piece));
        }

        view.getBoard().addListener(new BoardListener());
        view.getBoard().setDisplayedCounter(model.getState().getCounter());

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
                if (piece!=selectedPiece && piece.intersection(possiblePosition)) {
                    return;
                }
            }

            boolean win = selectedPiece.move(possiblePosition);
            view.getBoard().getSelectedPieceRepresentation().setBounds(possiblePosition);

            if (win) {
                view.winMessage();
            }

            view.getBoard().getSelectedPieceRepresentation().setBorder(false);
            model.getState().setSelectedPiece(null);

            model.getState().incrementCounter();
            view.getBoard().setDisplayedCounter(model.getState().getCounter());
        }
    }


    class BoardListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            move(e);
        }

    }

    class PieceListener extends MouseAdapter {

        private final PieceRepresentation pieceRepresentationControlled;
        private Piece pieceControlled;

        public PieceListener(PieceRepresentation pieceRepresentation) {
            pieceRepresentationControlled = pieceRepresentation;

            for (Piece piece : model.getState().getCurrent_config()) {
                if (piece.getPosition().contains(pieceRepresentationControlled.getBounds())) {
                    this.pieceControlled = piece;
                    return;
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            model.getState().setSelectedPiece(pieceControlled);
            view.getBoard().selectPiece(pieceRepresentationControlled);
        }

    }

}


