import Controller.Controller;
import Model.KlotskiModel;
import View.KlotskiUI;

public class Klotski {
    public Klotski(){
        Controller controller = new Controller(new KlotskiUI(), new KlotskiModel());
    }

}
