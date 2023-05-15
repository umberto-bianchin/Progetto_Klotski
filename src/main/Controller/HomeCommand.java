package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import java.awt.event.MouseEvent;

class HomeCommand extends UIController{

    /**
     * The class is a UI controller that handles the return at the start screen
     */
    HomeCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiUI.initStart();
    }

}
