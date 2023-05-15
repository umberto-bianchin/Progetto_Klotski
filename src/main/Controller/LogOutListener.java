package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.event.MouseEvent;

/**
 * The class is a UI controller that handles the logout of a user
 */
class LogOutListener extends UIController{

    LogOutListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiModel.logout();
        klotskiUI.logout();
    }

}
