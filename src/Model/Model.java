package Model;


import java.awt.*;
import java.io.IOException;

public class Model {

private final State state;

    public Model() {

        Rectangle[] initial = {new Rectangle(0, 0, 100, 200), new Rectangle(0, 200, 100, 200),
                new Rectangle(0, 400, 100, 100), new Rectangle(100, 0, 200, 200), new Rectangle(100, 200, 200, 100),
                new Rectangle(100, 300, 100, 100),new Rectangle(200, 300, 100, 100), new Rectangle(300, 0, 100, 200),
                new Rectangle(300, 200, 100, 200), new Rectangle(300, 400, 100, 100)};

        state = new State(initial);

    }

    public State getState(){
        return state;
    }

}
