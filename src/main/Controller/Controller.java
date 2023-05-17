package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.*;


public class Controller {
    private final KlotskiUI klotskiUI;
    private final KlotskiModel klotskiModel;


    /**
     * Initialize the object and check the argument if null. Display the game UI
     */
    public Controller(KlotskiUI klotskiUI, KlotskiModel klotskiModel) {

        if(klotskiUI == null || klotskiModel == null)
            throw new NullPointerException();

        this.klotskiUI = klotskiUI;
        this.klotskiModel = klotskiModel;
        initStart();
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
                klotskiModel.closeDatabaseConnection();
            }
        };
        SaveCommand saveCommand = new SaveCommand(klotskiModel, klotskiUI);

        klotskiUI.addStartListener(exit, new ConfigurationListener(klotskiModel, klotskiUI));
        klotskiUI.addAuthenticationListeners(new AuthListener(klotskiModel, klotskiUI), new DisconnectionListener(klotskiModel, klotskiUI), new SavedGamesListListener(klotskiModel, klotskiUI, saveCommand));
        klotskiUI.addGameBoardListeners(new BoardListener(klotskiModel, klotskiUI), new BlockListener(klotskiModel, klotskiUI));
        klotskiUI.addButtonsListeners(new RestartCommand(klotskiModel, klotskiUI), saveCommand, new NextCommand(klotskiModel, klotskiUI), new UndoCommand(klotskiModel, klotskiUI), new HomeCommand(klotskiModel, klotskiUI, saveCommand));

    }

}



