package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller {

    private final View view;
    private final Model model;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;

        view.addConfigurationListener(new ConfigurationListener());

    }

    private void start(int num_config) {

        model.initState(num_config);
        view.initGame(model.getInitialPositions(num_config));

        view.addBlockListener(new BlockListener());

        view.addBoardListener(new BoardListener());
        view.setDisplayedCounter(model.getCounter());

        ActionListener[] actionListeners = {new RestartCommand(), new SaveCommand(), new NextCommand(), new UndoCommand(), new HomeCommand()};
        view.addButtonsListener(actionListeners);

    }

    private void move(Point p) {

        Rectangle possiblePosition = model.moveSelectedPiece(p);
        if(possiblePosition == null)
            return;

        if (model.hasWin()) {
            view.winMessage();
        }

        view.moveSelectedBlock(possiblePosition);
        view.selectBlock(null);
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
            view.selectBlock(e.getComponent());
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

            model.restartState();
            view.restart(model.getInitialPositions());

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

            view.selectBlock(final_location.x, final_location.y);
            view.moveSelectedBlock(initial_position);

            view.setDisplayedCounter(model.getCounter());
            view.selectBlock(null);

        }
    }

    class HomeCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.restartState();
            view.initStart();
        }
    }

    class ConfigurationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int num_config = Integer.parseInt(((JButton)e.getSource()).getName());
            start(num_config);
        }
    }
}



