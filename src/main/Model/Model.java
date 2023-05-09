package Model;

import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class Model {

    private State state;
    private Database db;

    public void initState(int config) throws SQLException {
        state = new State(db.getInitialConfig(config), config);
    }

    public void resumeState(String name_game) throws SQLException {
        state = new State(db.getSavedMoves(name_game), db.getInitialConfig(db.getIdConf(name_game)), db.getFinalConfig(name_game), db.getIdConf(name_game));
    }

    public void restartState() {
        state.setCounter(0);
        state.setCurrentConfig(getInitialPositions());
        setSelectedPiece(null);
    }

    public void initDatabase() throws Exception {
        db = new Database();
    }

    public Rectangle[] getInitialPositions() {
        return state.getInitialPositions();
    }

    public Rectangle[] getCurrentPositions() {
        return state.getCurrentPositions();
    }

    public void setSelectedPiece(Point p) {
        state.setSelectedPiece(p);
    }

    public Rectangle moveSelectedPiece(Point p) {
        return state.moveSelectedPiece(p);
    }

    public boolean hasWin() {
        return state.getWin();
    }

    public int getCounter() {
        return state.getCounter();
    }


    public Move undo() {
        if(state.getCounter()==0)
            throw new RuntimeException();

        return state.undo();
    }

    public void login(String username, String password) throws Exception {

        if (!db.login(username, password))
            throw new RuntimeException("Invalid username or password");

    }

    public void registration(String username, String password) throws Exception {

        if (username.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException("Can't register players with black username or password");

        if (!db.registration(username, password))
            throw new RuntimeException("Can't register another player with the same username");

    }

    public void logout() {
        db.resetIdPlayer();
    }

    public void saveGame(String name) throws Exception {

        if (name == null)
            throw new NullPointerException("");

        if (!db.isLogged())
            throw new IllegalAccessException("You must login to save games");

        if (name.isBlank())
            throw new IllegalArgumentException("You can't save match with blank names");

        if (!db.saveGame(state.getMoves(), state.getInitialConfig(), state.getCurrentPositions(), name))
            throw new IllegalArgumentException("You can't save more than one match with the same name");

    }

    public Vector<String> getSavedGameList() throws SQLException {
        return db.getGameList();
    }

    public void deleteSavedGame(String name) throws SQLException {
        db.deleteGame(name);
    }

    public Move nextBestMove() throws Exception {
        Move bestMove = Solver.nextBestMove(state.getCurrentPositions());
        setSelectedPiece(bestMove.getInitialPosition().getLocation());
        moveSelectedPiece(bestMove.getFinalPosition().getLocation());
        return bestMove;

    }


}
