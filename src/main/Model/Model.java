package Model;

import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class Model {

private State state;
private final Database db = new Database();

    public void initState(int config){
        try {
            state = new State(db.getInitialConfig(config), config);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void resumeState(int idGame) throws SQLException {
        state = new State(db.getSavedMoves(idGame), db.getInitialConfig(idGame), db.getFinalConfig(idGame), db.getIdConf(idGame));
    }

    public void restartState(){
        state.setCounter(0);
        state.setCurrentConfig(getInitialPositions());
        setSelectedPiece(null);
    }

    public Rectangle[] getInitialPositions(){
        return state.getInitialPositions();
    }

    public Rectangle[] getCurrentPositions(){return state.getCurrentPositions();}

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

    public boolean login(String username, String password) throws SQLException {
        return db.login(username, password);
    }

    public boolean registration(String username, String password) throws SQLException {
        return db.registration(username, password);
    }

    public void logout(){
        db.resetIdPlayer();
    }

    public boolean saveGame(String name) throws SQLException {
        return db.saveGame(state.getMoves(), state.getInitialConfig(), state.getCurrentPositions());
        // TODO: 05/05/2023 add game name
    }

    public boolean isLogged(){
        return true;
        // TODO: 05/05/2023 implements from database
    }

    public Vector<String> getGameList() throws SQLException {
        return db.getGameList();
    }
}
