package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Controller {
    private final KlotskiUI klotskiUI;
    private final KlotskiModel klotskiModel;


    public Controller(KlotskiUI klotskiUI, KlotskiModel klotskiModel) {

        this.klotskiUI = klotskiUI;
        this.klotskiModel = klotskiModel;
        initStart();
    }

    /**
     * Initialize the Database connection and manage its closure with a WindowAdapter, called when closing the window.
     * Display the Start screen and add the needed listener (Auth buttons and the Levels buttons)
     */
    private void initStart(){

        WindowAdapter exit = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                klotskiModel.closeDatabaseConnection();
            }
        };

        try{
            klotskiModel.initDatabase();
        } catch(Exception e){
            klotskiUI.showMessage("Server SQL error", "Start", JOptionPane.ERROR_MESSAGE); }

        klotskiUI.initStart();
        klotskiUI.addStartListener(exit, new ConfigurationListener(klotskiModel, klotskiUI, this));
        klotskiUI.addAuthenticationListeners(new AuthListener(klotskiModel, klotskiUI), new LogOutListener(klotskiModel, klotskiUI), new SavedListener(klotskiModel, klotskiUI, this));

    }

    /**
     * Display the selected Game, used when the player select a game from the start screen or a saved game.
     * Add the needed listener (Buttons controller and the Mouse Listener in order to move the blocks)
     * @param piecesPosition starting blocks position
     * @param counter steps played of the match
     */
    void initGameView(Rectangle[] piecesPosition, int counter) {

        klotskiUI.initGame(piecesPosition, counter);
        klotskiUI.addGameBoardListeners(new BoardListener(klotskiModel, klotskiUI, this), new BlockListener(klotskiModel, klotskiUI));
        klotskiUI.addButtonsListeners(new RestartCommand(klotskiModel, klotskiUI), new SaveCommand(klotskiModel, klotskiUI), new NextCommand(klotskiModel, klotskiUI, this), new UndoCommand(klotskiModel, klotskiUI), new HomeCommand(klotskiUI));

    }

    /**
     * Check if the game is ended, if so, show the user a message and then display the start screen
     */
    void winHandler(){
        if (klotskiModel.hasWin()){
            klotskiUI.showMessage("You won!", "Win", JOptionPane.INFORMATION_MESSAGE);
            klotskiUI.initStart();
        }
    }


}



