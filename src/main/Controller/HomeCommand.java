package Controller;

import View.KlotskiUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HomeCommand implements ActionListener {

    private final KlotskiUI klotskiUI;

    HomeCommand(KlotskiUI klotskiUI) {
        this.klotskiUI = klotskiUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        klotskiUI.initStart();
    }

}
