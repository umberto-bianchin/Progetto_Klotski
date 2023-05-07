package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Locale;


public class Controller {

    private final View view;
    private final Model model;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;

        model.initDatabase();
        view.initStart();

        view.addConfigurationListener(new ConfigurationListener());
        view.addAuthListener(new AuthListener());
        view.addLogOutListener(new LogOutListener());
        view.addSavedListener(new SavedListener());

    }

    private void initBoardListener() {
        view.addBlockListener(new BlockListener());
        view.addBoardListener(new BoardListener());

        ActionListener[] actionListeners = {new RestartCommand(), new SaveCommand(), new NextCommand(), new UndoCommand(), new HomeCommand()};
        view.addButtonsListener(actionListeners);

    }

    private void move(Point p) {

        Rectangle possiblePosition = model.moveSelectedPiece(p);

        if (possiblePosition == null)
            return;

        if (model.hasWin()) {
            view.showMessage("You won!", "Win", JOptionPane.INFORMATION_MESSAGE);
        }

        view.moveSelectedBlock(possiblePosition, model.getCounter());
    }


    class BoardListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            move(e.getPoint());
        }
    }

    class BlockListener extends MouseAdapter {

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

            String name = view.askGameName();

            try {
                model.saveGame(name);
                view.showMessage("Successfully saved the game.", "Save", JOptionPane.INFORMATION_MESSAGE);
            } catch (NullPointerException ignored) {
            } catch (Exception ex) {
                view.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    class UndoCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (model.getCounter() == 0)
                return;

            Rectangle initial_position = model.getLastMove().getInitialPosition();
            Point final_location = model.getLastMove().getFinalPosition().getLocation();

            model.undo();
            view.undo(initial_position, final_location, model.getCounter());

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
            int num_config = Integer.parseInt(((JButton) e.getSource()).getName());

            model.initState(num_config);
            view.initGame(model.getInitialPositions(), 0);

            initBoardListener();
        }
    }

    class AuthListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = ((JButton) e.getSource()).getClientProperty("username").toString();
            String password = ((JButton) e.getSource()).getClientProperty("password").toString();

            String type = ((JButton) e.getSource()).getText();

            try {

                if (type.equals("Log in")) {
                    model.login(username, password);
                } else if (type.equals("Sign in")) {
                    model.registration(username, password);
                }

                view.showMessage("Hi " + username + "! You have successfully " + type.toLowerCase(Locale.ROOT), "type", JOptionPane.INFORMATION_MESSAGE);
                view.initUser(username);


            } catch (SQLException ex) {
                view.showMessage("Database error, retry later", type, JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                view.showMessage(ex.getMessage(), type, JOptionPane.ERROR_MESSAGE);
            }


        }
    }

    class LogOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.logout();
            view.logout();
        }
    }

    class SavedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.showSavedGames(model.getGameList(), new SelectSavedGamesListener());
            } catch (SQLException ex) {
                view.showMessage("Database error, retry later", "Saved Games", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SelectSavedGamesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                if (((JButton) e.getSource()).getName().startsWith("game")) {

                    model.resumeState(((JButton) e.getSource()).getName().substring(4));
                    view.initGame(model.getCurrentPositions(), model.getCounter());
                    initBoardListener();

                } else if (((JButton) e.getSource()).getName().startsWith("delete")) {
                    model.delete(((JButton) e.getSource()).getName().substring(6));
                    view.showSavedGames(model.getGameList(), new SelectSavedGamesListener());
                }

            } catch (SQLException ex) {
                view.showMessage("Database error, retry later", "Saved Games", JOptionPane.ERROR_MESSAGE);
            }

        }
    }


}



