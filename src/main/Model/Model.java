package Model;

import java.awt.*;
import java.util.Vector;

public class Model {

private State state;

Vector<Rectangle[]> initial_conf = new Vector<>();

    public Model() {

        Rectangle[] initial1 = {new Rectangle(0, 0, 100, 200), new Rectangle(0, 200, 100, 200),
                new Rectangle(0, 400, 100, 100), new Rectangle(100, 0, 200, 200), new Rectangle(100, 200, 200, 100),
                new Rectangle(100, 300, 100, 100),new Rectangle(200, 300, 100, 100), new Rectangle(300, 0, 100, 200),
                new Rectangle(300, 200, 100, 200), new Rectangle(300, 400, 100, 100)};

        Rectangle[] initial2 = {new Rectangle(0, 0, 100, 200), new Rectangle(0, 200, 100, 200),
                new Rectangle(100, 0, 200, 200), new Rectangle(100, 200, 100, 100), new Rectangle(100, 300, 100, 100),
                new Rectangle(100, 400, 200, 100),new Rectangle(300, 0, 100, 200), new Rectangle(300, 200, 100, 200),
                new Rectangle(200, 200, 100, 100), new Rectangle(200, 300, 100, 100)};

        Rectangle[] initial3 = {new Rectangle(0, 0, 100, 100), new Rectangle(0, 100, 100, 200),
                new Rectangle(0, 300, 100, 100), new Rectangle(0, 400, 200, 100), new Rectangle(100, 0, 200, 200),
                new Rectangle(100, 200, 100, 200),new Rectangle(300, 0, 100, 100), new Rectangle(300, 100, 100, 200),
                new Rectangle(300, 300, 100, 100), new Rectangle(200, 400, 200, 100)};

        Rectangle[] initial4 = {new Rectangle(0, 0, 100, 200), new Rectangle(0, 200, 100, 200),
                new Rectangle(100, 0, 100, 100), new Rectangle(100, 100, 100, 200), new Rectangle(100, 300, 200, 100),
                new Rectangle(200, 0, 100, 100),new Rectangle(200, 100, 200, 200), new Rectangle(300, 0, 100, 100),
                new Rectangle(300, 300, 100, 100), new Rectangle(200, 400, 200, 100)};


        initial_conf.add(initial1);
        initial_conf.add(initial2);
        initial_conf.add(initial3);
        initial_conf.add(initial4);

    }


    public void initState(int config){
        state = new State(initial_conf.get(config), config);
    }

    public void restartState(){
        resetCounter();
        state.setCurrentConfig(getInitialPositions());
        setSelectedPiece(null);
    }

    public Rectangle[] getInitialPositions(int i){
        return initial_conf.get(i);
    }

    public Rectangle[] getInitialPositions(){
        return state.getInitialPositions();
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
