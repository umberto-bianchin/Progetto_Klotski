package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The UIController class is an abstract class that serves as a base for UI controllers in the Klotski game
 * It provides common functionality and defines an abstract method for handling mouse events
 */
abstract class UIController extends MouseAdapter {

    protected final KlotskiModel klotskiModel;
    protected final KlotskiUI klotskiUI;

    /**
     * Constructs a new object with the specified Klotski model and UI
     * @throws NullPointerException if either the model or the view is null
     */
    UIController(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        if(klotskiModel == null || klotskiUI == null)
            throw new NullPointerException("model or view null");

        this.klotskiModel = klotskiModel;
        this.klotskiUI = klotskiUI;
    }

    /**
     * Check if the game is ended, if so, show the user a message and then display the start screen
     */
    protected void winHandler(){
        if (klotskiModel.hasWin()){
            klotskiUI.showMessage("You won!", "Win", JOptionPane.INFORMATION_MESSAGE);
            klotskiUI.initStart();
        }
    }

    @Override
    public abstract void mousePressed(MouseEvent e);


}
