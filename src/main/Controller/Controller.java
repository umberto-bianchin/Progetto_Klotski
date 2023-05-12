package Controller;

import Model.Model;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Controller {
    private final View view;
    private final Model model;


    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        initStart();
    }

    private void initStart(){

        WindowAdapter exit = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                model.closeDatabaseConnection();
            }
        };

        try{
            model.initDatabase();
            view.initStart();
            view.addConfigurationListener(exit, new ConfigurationListener(model, view, this));
            view.addAuthenticationListeners(new AuthListener(model, view), new LogOutListener(model, view), new SavedListener(model, view, this));
        } catch(Exception e){
            view.showMessage("Server SQL error", "Start", JOptionPane.ERROR_MESSAGE);
        }

    }

    void initGameView(Rectangle[] currentPositions, int counter) {

        view.initGame(currentPositions, counter);
        view.addGameBoardListeners(new BoardListener(model, view, this), new BlockListener(model, view));
        view.addButtonsListeners(new RestartCommand(model, view), new SaveCommand(model, view), new NextCommand(model, view, this), new UndoCommand(model, view), new HomeCommand(model, view));

    }

    void WinHandler(){
        if (model.hasWin()){
            view.showMessage("You won!", "Win", JOptionPane.INFORMATION_MESSAGE);
            model.restartState();
            view.initStart();
        }
    }

//    void executeWithTryCatch(CodeBlock code) {
//        try {
//            code.execute();
//        } catch (SQLException ex) {
//            view.showMessage(ex.getMessage(), "Saved Games", JOptionPane.ERROR_MESSAGE);
//        } catch (IOException | ParseException ex){
//
//        }
//    }

}



