package Controller;

import Model.Model;
import Model.Move;
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

        try{
            model.initDatabase();
            view.initStart();
            view.addConfigurationListener(new ConfigurationListener());
            view.addAuthenticationListeners(new AuthListener(), new LogOutListener(), new SavedListener());
        } catch(Exception e){
            view.showMessage("Server SQL error", "Start", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void initGameView(Rectangle[] currentPositions, int counter) {

        view.initGame(currentPositions, counter);
        view.addGameBoardListeners(new BoardListener(), new BlockListener());
        view.addButtonsListeners(new RestartCommand(), new SaveCommand(), new NextCommand(), new UndoCommand(), new HomeCommand());

    }


    class BoardListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            try {
                Rectangle finalPosition = model.moveSelectedPiece(e.getPoint());
                view.moveSelectedBlock(finalPosition, model.getCounter());

                if (model.hasWin()){
                    view.showMessage("You won!", "Win", JOptionPane.INFORMATION_MESSAGE);
                    model.restartState();
                    view.initStart();
                }
            }
            catch (RuntimeException ignored){}

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
            try {
                Move bestMove = model.nextBestMove();
                view.makeMove(bestMove, model.getCounter());

                if (model.hasWin()){
                    view.showMessage("You won!", "Win", JOptionPane.INFORMATION_MESSAGE);
                    model.restartState();
                    view.initStart();
                }

            } catch (Exception ex) {
                view.showMessage("Connectivity problems using solver, retry later", "Solver", JOptionPane.ERROR_MESSAGE);
            }
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
                view.showMessage("Successfully saved the game", "Save", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                view.showMessage(ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    class UndoCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                Move lastMove = model.undo();
                view.makeMove(lastMove.invert(), model.getCounter());
            }
            catch (RuntimeException ignored){}

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

            try {
                int num_config = Integer.parseInt(((JButton) e.getSource()).getName());
                model.initState(num_config);
                initGameView(model.getInitialPositions(), 0);
            }
            catch (SQLException ex){
                view.showMessage(ex.getMessage(), "Game Selector", JOptionPane.ERROR_MESSAGE);
            }
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

                view.showMessage("Hi " + username + "! You have successfully " + type.toLowerCase(Locale.ROOT), type, JOptionPane.INFORMATION_MESSAGE);
                view.initUser(username);

            }  catch (Exception ex) {
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
                view.showSavedGames(model.getSavedGameList(), new SelectSavedGamesListener());
            } catch (SQLException ex) {
                view.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SelectSavedGamesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                if (((JButton) e.getSource()).getName().startsWith("game")) {

                    model.resumeState(((JButton) e.getSource()).getName().substring(4));
                    initGameView(model.getCurrentPositions(), model.getCounter());

                } else if (((JButton) e.getSource()).getName().startsWith("delete")) {

                    model.deleteSavedGame(((JButton) e.getSource()).getName().substring(6));
                    view.showSavedGames(model.getSavedGameList(), new SelectSavedGamesListener());
                }

            } catch (SQLException ex) {
                view.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}



