import Controller.Controller;
import Model.Model;
import View.View;

public class Main {
    public static void main(String[] args) {

        View view = new View();
        Model model = new Model();

        Controller game = new Controller(view, model);

    }
}