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

        if(possiblePosition == null)
            return;

        if (model.hasWin()) {
            view.showMessage(true, "win");
        }

        view.moveSelectedBlock(possiblePosition, model.getCounter());
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
            if(model.isLogged()) {
                String name = view.askName();
                try {
                    boolean saved = model.saveGame(name);
                    view.showMessage(saved, "save");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                view.showMessage(false, "not logged in");
            }
        }
    }

    class UndoCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(model.getCounter() == 0)
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
            int num_config = Integer.parseInt(((JButton)e.getSource()).getName());

            model.initState(num_config);
            view.initGame(model.getInitialPositions(), 0);

            initBoardListener();
        }
    }

    class AuthListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String user = ((JButton)e.getSource()).getClientProperty( "username" ).toString();
            String password = ((JButton)e.getSource()).getClientProperty( "password" ).toString();

            String type = ((JButton)e.getSource()).getText();

            if(type.equals("Log in")) {
                boolean authenticated;
                try {
                    authenticated = model.login(user, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                view.showAuthResult(authenticated, "l", user);

            }
            else if (type.equals("Sign up")){
                boolean sign_up;
                if(user.isEmpty() || password.isEmpty()) {
                    view.showAuthResult(false, "s", null);
                    return;
                }
                try {
                    sign_up = model.registration(user, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                view.showAuthResult(sign_up, "s", user);
            }

        }
    }

    class LogOutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.logout();
            view.logout();
        }
    }

    class SavedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.showSavedGames(model.getGameList(), new SelectSavedGamesListener());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class SelectSavedGamesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(((JButton)e.getSource()).getName().startsWith("game")){
                try {
                    model.resumeState(((JButton)e.getSource()).getName().substring(4));
                    view.initGame(model.getCurrentPositions(), model.getCounter());

                    initBoardListener();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(((JButton)e.getSource()).getName().startsWith("delete")){
                try{
                    if(model.delete(((JButton)e.getSource()).getName().substring(6)))
                        view.showSavedGames(model.getGameList(), new SelectSavedGamesListener());
                    else
                        view.showMessage(false, "delete");

                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        }
    }




}



