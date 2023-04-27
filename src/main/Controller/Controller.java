package Controller;

import View.*;
import Model.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class Controller {

    private View view;
    private Model model;


    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;

        for (Piece piece : view.getBoard().getPieces()) {
            piece.addListener(new PieceListener(piece));
        }

        view.getBoard().addListener(new BoardListener(view.getBoard()));

    }

        class BoardListener extends MouseAdapter{

            private Board controlledBoard;


            public BoardListener(Board board){
                controlledBoard = board;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                controlledBoard.move(e);
            }

        }

        class PieceListener extends MouseAdapter {

            private Piece pieceControlled;

            public PieceListener(Piece piece){
                pieceControlled = piece;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Board.selectPiece(pieceControlled);
            }

        }

    }



