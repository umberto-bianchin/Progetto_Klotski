package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.*;


public class Controller extends UIController{


    /**
     * Initialize the object and check if the argument are null. Display the game UI
     */
    public Controller(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
        initStart();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiModel.closeDatabaseConnection();
    }

    /**
     * Initialize the Database connection and manage its closure with a WindowAdapter, called when closing the window.
     * Display the Start screen and add the needed listener
     */
    private void initStart(){

        try{
            klotskiModel.initDatabase();
            klotskiUI.initStart();
        } catch(Exception e){
            klotskiUI.showMessage("Server SQL error", "Start", JOptionPane.ERROR_MESSAGE); }

        addListener();
    }

    /**
     * Add the needed Listener of the UI
     */
    private void addListener() {
        WindowAdapter exit = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mousePressed(null);
            }
        };
        SaveCommand saveCommand = new SaveCommand(klotskiModel, klotskiUI);

        klotskiUI.addStartListener(exit, new ConfigurationListener(klotskiModel, klotskiUI));
        klotskiUI.addAuthenticationListeners(new AuthListener(klotskiModel, klotskiUI), new DisconnectionListener(klotskiModel, klotskiUI), new SavedGamesListListener(klotskiModel, klotskiUI, saveCommand));
        klotskiUI.addGameBoardListeners(new BoardListener(klotskiModel, klotskiUI), new BlockListener(klotskiModel, klotskiUI));
        klotskiUI.addButtonsListeners(new RestartCommand(klotskiModel, klotskiUI), saveCommand, new NextCommand(klotskiModel, klotskiUI), new UndoCommand(klotskiModel, klotskiUI), new HomeCommand(klotskiModel, klotskiUI, saveCommand));

    }

}



