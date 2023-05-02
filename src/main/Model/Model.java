package Model;

import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class Model {

private State state;

Vector<Rectangle[]> initial_conf = new Vector<>();
private Database db = new Database();

    public Model() throws Exception {

        initial_conf.add(db.getInitialConfig(0));
        initial_conf.add(db.getInitialConfig(1));
        initial_conf.add(db.getInitialConfig(2));
        initial_conf.add(db.getInitialConfig(3));


    }


    public void initState(int config){
        //state = new State(initial_conf.get(config), config);

    }

    public void restartState(){
        state.setCounter(0);
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

    public Move getLastMove(){
        return state.getLastMove();
    }

    public void undo(){
        state.undo();
    }


}
