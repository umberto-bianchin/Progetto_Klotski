import Controller.Controller;
import Model.Model;
import View.View;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {

        View view = new View();
        Model model = new Model();

        Controller game = new Controller(view, model);

    }
}

