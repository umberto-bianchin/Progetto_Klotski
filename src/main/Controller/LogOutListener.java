package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;

import java.awt.event.MouseEvent;

class LogOutListener extends UIController{

    LogOutListener(KlotskiModel klotskiModel, KlotskiUI klotskiUI) {
        super(klotskiModel, klotskiUI);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiModel.logout();
        klotskiUI.logout();
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        klotskiModel.logout();
//        klotskiUI.logout();
//    }

}
