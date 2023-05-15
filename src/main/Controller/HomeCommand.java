package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import java.awt.event.MouseEvent;

class HomeCommand extends UIController{

    HomeCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiUI.initStart();
    }

}
