package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;

class UIController extends MouseAdapter {

    protected final KlotskiModel klotskiModel;
    protected final KlotskiUI klotskiUI;

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

}
