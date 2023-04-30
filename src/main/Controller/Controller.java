package Controller;

import Model.Model;
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

        view.setPositionBlocks(model.getInitialPositions());

        view.addBlockListener(new BlockListener());

        view.addBoardListener(new BoardListener());
        view.setDisplayedCounter(model.getCounter());

        ActionListener[] actionListeners = {new RestartCommand(), new SaveCommand(), new NextCommand(), new UndoCommand()};

        view.getButtons().addCommand(actionListeners);

    }

    private void move(Point p) {

        Rectangle possiblePosition = model.moveSelectedPiece(p);
        if(possiblePosition == null)
            return;

        if (model.hasWin()) {
            view.winMessage();
        }

        view.moveSelectedBlock(possiblePosition);
        view.selectBlock((Block) null);
        view.setDisplayedCounter(model.getCounter());

    }


    class BoardListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            move(e.getPoint());
        }
    }

    class BlockListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            model.setSelectedPiece(e.getComponent().getLocation());
            view.selectBlock((Block) e.getSource());
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
                view.setPositionBlocks(model.getInitialPositions());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            model.setCurrentConfig(model.getInitialPositions());

            model.setSelectedPiece(null);
            view.selectBlock((Block) null);

            model.resetCounter();
            view.setDisplayedCounter(model.getCounter());

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

            if(model.getCounter() == 0)
                return;

            Rectangle initial_position = model.getLastMove().getInitialPosition();
            Point final_location = model.getLastMove().getFinalPosition().getLocation();

            model.undo(initial_position, final_location);

            view.selectBlock(final_location);
            view.moveSelectedBlock(initial_position);

            view.setDisplayedCounter(model.getCounter());
            view.selectBlock((Block) null);

        }
    }
}



