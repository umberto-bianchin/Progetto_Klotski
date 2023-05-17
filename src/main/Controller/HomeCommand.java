package Controller;

import Model.KlotskiModel;
import View.KlotskiUI;
import java.awt.event.MouseEvent;

class HomeCommand extends UIController{

    final SaveCommand save;

    /**
     * The class is a UI controller that handles the return at the start screen
     */
    HomeCommand(KlotskiModel klotskiModel, KlotskiUI klotskiUI, SaveCommand save) {
        super(klotskiModel, klotskiUI);
        this.save = save;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        klotskiUI.initStart();
        //update the current name inside the save listener
        save.setName(null);

    }

}
