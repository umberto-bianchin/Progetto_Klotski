package Model;

import java.awt.*;

public class Model {

private final State state;

    public Model() {

        Rectangle[] initial = {new Rectangle(0, 0, 100, 200), new Rectangle(0, 200, 100, 200),
                new Rectangle(0, 400, 100, 100), new Rectangle(100, 0, 200, 200), new Rectangle(100, 200, 200, 100),
                new Rectangle(100, 300, 100, 100),new Rectangle(200, 300, 100, 100), new Rectangle(300, 0, 100, 200),
                new Rectangle(300, 200, 100, 200), new Rectangle(300, 400, 100, 100)};

        state = new State(initial);

    }

    public Rectangle[] getInitialPositions(){
        return state.getInitialPositions();
    }

    public void setCurrentConfig(Rectangle[] positions){
        state.setCurrentConfig(positions);
    }

    public void setSelectedPiece(Point p){
        state.setSelectedPiece(p);
    }

    public Rectangle moveSelectedPiece(Point p){
        return state.moveSelectedPiece(p);
    }

    public boolean hasWin(){
        return state.getWin();
    }

    public int getCounter(){
        return state.getCounter();
    }

    public void resetCounter(){
        state.setCounter(0);
    }

    public Move getLastMove(){
        return state.getLastMove();
    }

    public void undo(Rectangle initialPosition, Point finalLocation){
        state.undo(initialPosition, finalLocation);
    }


}
