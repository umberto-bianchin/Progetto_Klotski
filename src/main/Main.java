import Controller.Controller;
import Model.Model;

import View.View;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        View view = new View();
        Model model = new Model();


        Controller game = new Controller(view, model);

    }
}