package Model;

import java.awt.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Vector;

/**
 * The Database class provides methods to interact with database hosted by AWS for saving and retrieving game data
 */
public class Database {

    private final Connection conn;
    private int id_player = -1;

    /**
     * Constructs a new Database object and establishes a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public Database() throws SQLException {
        String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
        conn = DriverManager.getConnection(dbURL, "admin", "mypassword");
    }

    /**
     * @param moves The list of moves made in the game
     * @param game_id The initial configuration of the game (0-4)
     * @param final_positions The final configuration of the game
     * @param game_name The name of the game
     * @return true if the game is successfully saved, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws IllegalAccessException if the user is not logged in
     */
    public boolean saveGame(LinkedList<Move> moves, int game_id, Rectangle[] final_positions, String game_name) throws SQLException, IllegalAccessException {

        if (!isLogged())
            throw new IllegalAccessException("You must login to save games");

        String query = "SELECT ID_GAME FROM games WHERE name = '" + game_name + "' AND ID_USER =" + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next())
                return false;

            query = "INSERT INTO games (ID_USER,ID_CONF, name) VALUES (" + id_player + "," + game_id + ",'" + game_name + "');"; //inserisce il game (autoincrementa)
            stmt.execute(query);
        }

        query = "SELECT ID_GAME FROM games WHERE ID_USER = " + id_player + " ORDER BY ID_GAME DESC LIMIT 1;"; //prende l'id del game inserito

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            rs.next();
            int id_game = rs.getInt("ID_GAME");

            for (Move move : moves) {
                query = "INSERT INTO saved_move(ID_GAME,ID_USER,width,height,x_ini,y_ini,x_fin,y_fin) " +
                        "VALUES(" + id_game + "," + id_player + "," + move.getInitialPosition().width + "," + move.getInitialPosition().height + ","
                        + move.getInitialPosition().x + "," + move.getInitialPosition().y + "," + move.getFinalPosition().x + "," + move.getFinalPosition().y + ");";
                stmt.execute(query);
            }

            for (Rectangle rectangle : final_positions) {
                query = "INSERT INTO saved_state(ID_GAME, ID_USER, x,y,width,height) VALUES(" + id_game + ", " + id_player + ", " + rectangle.x + "," + rectangle.y + "," + rectangle.width + "," + rectangle.height + ");";
                stmt.execute(query);
            }

            return true;
        }
    }

    /**
     * Retrieves the saved moves of a game from the database, empty if the game isn't found
     * @param game_name The name of the game in which the saved moves is needed
     * @return a LinkedList of Move objects representing the saved moves
     * @throws SQLException if a database access error occurs
     * @throws IllegalAccessException if the user is not logged in
     */
    public LinkedList<Move> getSavedMoves(String game_name) throws SQLException, IllegalAccessException{

        if (!isLogged())
            throw new IllegalAccessException("You must login");

        LinkedList<Move> moves = new LinkedList<>();
        String query = "SELECT * FROM saved_move WHERE ID_GAME IN (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int x_ini = rs.getInt("x_ini");
                int y_ini = rs.getInt("y_ini");
                int x_fin = rs.getInt("x_fin");
                int y_fin = rs.getInt("y_fin");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                Move temp = new Move(new Rectangle(x_ini, y_ini, width, height), new Rectangle(x_fin, y_fin, width, height));
                moves.addLast(temp);
            }
        }

        return moves;

    }

    /**
     * Retrieves the ID of the game configuration with the given name
     * @return the ID of the game configuration (0-4)
     * @throws SQLException if a database access error occurs or the game isn't found
     * @throws IllegalAccessException if the user is not logged in
     */
    public int getIdConfiguration(String game_name) throws SQLException, IllegalAccessException {

        if (!isLogged())
            throw new IllegalAccessException("You must login");

        String query = "SELECT ID_CONF FROM games WHERE ID_GAME = (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            rs.next();
            return rs.getInt("ID_CONF");
        }
    }

    /**
     * Retrieves the initial positions of a game from the database
     * @param game_id The ID of the game configuration.
     * @return an array of Rectangle[10] objects representing the initial positions.
     * @throws SQLException if a database access error occurs.
     */
    public Rectangle[] getInitialPositions(int game_id) throws SQLException {
        String query = "SELECT * FROM initial_state WHERE ID_CONF=" + game_id + ";";
        return getConfiguration(query);
    }

    /**
     * Retrieves the final positions of a game from the database
     * @param game_name name of the game (not null)
     * @return an array of Rectangle[10] objects representing the final positions
     * @throws SQLException if a database access error occurs
     * @throws IllegalAccessException if the user is not logged in
     */
    public Rectangle[] getFinalPositions(String game_name) throws SQLException, IllegalAccessException {

        if (!isLogged())
            throw new IllegalAccessException("You must login");

        String query = "SELECT * FROM saved_state WHERE ID_GAME IN (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";
        return getConfiguration(query);
    }


    private Rectangle[] getConfiguration(String query) throws SQLException {

        Rectangle[] configuration = new Rectangle[10];
        int count = 0;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                configuration[count] = new Rectangle(x, y, width, height);
                count++;
            }
        }
        return configuration;
    }

    /**
     * @return a Vector of game names associated with the logged-in user
     * @throws SQLException if a database access error occurs.
     * @throws IllegalAccessException if the user is not logged in.
     */
    public Vector<String> getGameList() throws SQLException, IllegalAccessException {

        if (!isLogged())
            throw new IllegalAccessException("You must login");

        Vector<String> gameList = new Vector<>();
        String query = "SELECT name FROM games WHERE ID_USER=" + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next())
                gameList.add(rs.getString("name"));

        }
        return gameList;
    }

    /**
     * @param game_name The name of the game to be deleted (not null)
     * @throws SQLException if a database access error occurs
     * @throws IllegalAccessException if the user is not logged in
     */
    public void deleteGame(String game_name) throws SQLException, IllegalAccessException {

        if (!isLogged())
            throw new IllegalAccessException("You must login");

        String query = "CALL delete_game('" + game_name + "')";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }

    }

    /**
     * @param username The username of the user (not null)
     * @param password The password of the user (not null)
     * @return true if the login is successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean login(String username, String password) throws SQLException {

        int pass = password.hashCode();
        int user = username.hashCode();

        String query = "SELECT ID_USER FROM users WHERE username='" + username + "' AND password=" + pass + ";";


        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                id_player = user + pass;
                return true;
            }
        }
        return false;
    }

    /**
     * Registers a new user with the given username and password
     * @param username The username of the new user (not null)
     * @param password The password of the new user (not null)
     * @return true if the registration is successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean registration(String username, String password) throws SQLException {

        String query = "CALL insert_user(?,?,?,?);";
        int user = username.hashCode();
        int pass = password.hashCode();
        int id = user + pass;

        try (Statement stmt = conn.createStatement();
             CallableStatement cstmt = conn.prepareCall(query)) {

            cstmt.setInt(1, id);
            cstmt.setString(2, username);
            cstmt.setInt(3, pass);
            cstmt.registerOutParameter(4, Types.INTEGER);
            cstmt.execute();

            int rowsAffected = cstmt.getInt(4);
            if (rowsAffected == 1) {
                id_player = id;
                return true;
            }

        }
        return false;

    }

    public void resetIdPlayer() {
        //logout
        id_player = -1;
    }

    /**
     * Closes the database connection and resets the ID of the currently logged-in player
     * @throws SQLException if a database access error occurs
     */
    public void closeConnection() throws SQLException{
        conn.close();
        resetIdPlayer();
    }

    public boolean isLogged() {
        return id_player != -1;
    }

    /**
     * Deletes all games associated with the logged-in user
     * @throws SQLException if a database access error occurs.
     * @throws IllegalAccessException if the user is not logged in.
     */
    public void deleteAllGames() throws SQLException, IllegalAccessException {
        Vector<String> gameList = getGameList();
        for(String name : gameList)
            deleteGame(name);
    }

    /**
     * Deletes a user from the database.
     * @param username The username of the user to be deleted (not null)
     */
    public void deleteUser(String username) throws SQLException{
        String query = "CALL delete_user('" + username + "')";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }
}




