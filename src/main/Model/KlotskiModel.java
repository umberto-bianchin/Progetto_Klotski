package Model;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class KlotskiModel {

    private State state;
    private Database db;
    private final Solver solver = new Solver();

    /**
     * Used when is opened a new game, ask the database for the necessary information to create a new State
     * @param id_configuration is the number of the desired configuration (1-4)
     * @throws SQLException when database raise an Exception (timeout)
     */
    public void initState(int id_configuration) throws SQLException {

        if(id_configuration<0 || id_configuration>=4)
            throw new IllegalArgumentException("configuration_id invalid");

        state = new State(db.getInitialConfig(id_configuration), id_configuration);
    }

    /**
     * Used when a game is resumed between ones of the already saved
     * @param name_game is the name of the saved game
     * @throws SQLException when database raise an Exception (timeout or not existing game)
     */
    public void resumeState(String name_game) throws SQLException {
        state = new State(db.getSavedMoves(name_game), db.getInitialConfig(db.getIdConf(name_game)), db.getFinalConfig(name_game), db.getIdConf(name_game));
    }

    /**
     * Used when a game is restarted, in order to reinitialize the State object
     */
    public void restartState() {
        state = new State(state.getInitialPositions(), state.getIdConfiguration());
    }

    /**
     * Necessary action to initialize the connection with the database, in order to use the application
     * @throws SQLException when database raise an Exception (timeout)
     */
    public void initDatabase() throws SQLException {
        db = new Database();
    }

    /**
     * @return array[10] representing the current positions of the pieces
     */
    public Rectangle[] getCurrentPositions() {
        return state.getCurrentPositions();
    }

    /**
     * @param p Point contained in the desired piece to select
     */
    public void setSelectedPiece(Point p) {
        state.setSelectedPiece(p);
    }

    /**
     * @param p Point contained in the desired final location of the selected piece
     * @return Move done
     * @throws RuntimeException when the move is invalid
     */
    public Move moveSelectedPiece(Point p) throws RuntimeException {
        return state.moveSelectedPiece(p);
    }

    public boolean hasWin() {
        return state.getWin();
    }

    public int getCounter() {
        return state.getMoves().size();
    }

    /**
     * @return last move reversed
     * @throws RuntimeException when there are no moves to undo
     */
    public Move undo() throws RuntimeException{
        if(getCounter()==0)
            throw new RuntimeException();

        return state.undo();
    }

    /**
     * @throws RuntimeException if no player is registered with those username and password or null value
     * @throws SQLException when database raise an Exception (timeout)
     */
    public void login(String username, String password) throws RuntimeException, SQLException {

        if (username == null || password == null || !db.login(username, password))
            throw new RuntimeException("Invalid username or password");

    }

    /**
     * @throws IllegalArgumentException when the form of the strings are invalid (null or empty)
     * @throws RuntimeException when there are conflicts inside database (two user with same username)
     * @throws SQLException when database raise an Exception (timeout)
     */
    public void registration(String username, String password) throws IllegalArgumentException, RuntimeException, SQLException {

        if (username == null || password == null || username.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException("Can't register players with black username or password");

        if (!db.registration(username, password))
            throw new RuntimeException("Can't register another player with the same username");

    }

    public void logout() {
        db.resetIdPlayer();
    }

    /**
     * @throws IllegalAccessException when the player isn't logged into the database
     * @throws SQLException when database raise an Exception (timeout)
     * @throws IllegalArgumentException when tha name is invalid (blank or already used)
     */
    public void saveGame(String name) throws IllegalArgumentException, IllegalAccessException, SQLException {

        if (name.isBlank())
            throw new IllegalArgumentException("You can't save match with blank names");

        if (!db.saveGame(state.getMoves(), state.getIdConfiguration(), state.getCurrentPositions(), name))
            throw new IllegalArgumentException("You can't save more than one match with the same name");

    }

    /**
     * @return names of found saved games of that logged player
     * @throws IllegalAccessException when the player isn't logged into the database
     * @throws SQLException when database raise an Exception (timeout)
     */
    public Vector<String> getSavedGameList() throws SQLException, IllegalAccessException {
        if (!db.isLogged())
            throw new IllegalAccessException("You have to be logged to view your games");

        return db.getGameList();
    }

    /**
     * Delete the game with that name, if not found the database call doesn't do anything
     * @param name game name that is wanted to be deleted
     * @throws SQLException when database raise an Exception (timeout)
     * @throws IllegalAccessException  when the player isn't logged into the database
     */
    public void deleteSavedGame(String name) throws SQLException, IllegalAccessException {
        if (!db.isLogged())
            throw new IllegalAccessException("You have to be logged to delete your games");

        db.deleteGame(name);
    }

    /**
     * @throws IOException if an error occurred with the POST request
     * @throws ParseException when the received JSON is invalid
     */
    public Move nextBestMove() throws IOException, ParseException {
        Move bestMove = solver.nextBestMove(state.getCurrentPositions());
        state.makeMove(bestMove);
        solver.setConfigurationHash(Arrays.hashCode(state.getCurrentPositions()));

        return bestMove;

    }

    public void closeDatabaseConnection(){
        try {
            db.closeConnection();
        } catch (SQLException | NullPointerException ignored) {}
        // catch NullPointerException if the initial connection was unsuccessful (db == null)
    }

}
