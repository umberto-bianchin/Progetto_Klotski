import Controller.Controller;
import Model.KlotskiModel;
import View.KlotskiUI;

/**
 * Class that represent the whole Klotski Game
 */
public class Klotski {
    public Klotski(){
        Controller controller = new Controller(new KlotskiModel(), new KlotskiUI());
    }

}
