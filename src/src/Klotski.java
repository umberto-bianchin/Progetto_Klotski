import Controller.Controller;
import Model.Model;
import View.View;

public class Klotski {
    public Klotski(){
        Controller controller = new Controller(new View(), new Model());
    }

}
