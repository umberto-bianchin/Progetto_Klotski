package Model;

import java.awt.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Vector;

public class Database {

    private final Connection conn;
    private int id_player = -1;

    public Database() throws SQLException {
        //database connection
//        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
        conn = DriverManager.getConnection(dbURL, "admin", "mypassword");
    }

    public boolean saveGame(LinkedList<Move> moves, int initial_config, Rectangle[] final_config, String game_name) throws SQLException, IllegalAccessException {

        if (id_player == -1)
            throw new IllegalAccessException("You must login to save games");

        String query = "SELECT ID_GAME FROM games WHERE name = '" + game_name + "' AND ID_USER =" + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next())
                return false;

            query = "INSERT INTO games (ID_USER,ID_CONF, name) VALUES (" + id_player + "," + initial_config + ",'" + game_name + "');"; //inserisce il game (autoincrementa)
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

            for (Rectangle rectangle : final_config) {
                query = "INSERT INTO saved_state(ID_GAME, ID_USER, x,y,width,height) VALUES(" + id_game + ", " + id_player + ", " + rectangle.x + "," + rectangle.y + "," + rectangle.width + "," + rectangle.height + ");";
                stmt.execute(query);
            }

            return true;
        }
    }

    public LinkedList<Move> getSavedMoves(String game_name) throws SQLException{
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


    public int getIdConf(String game_name) throws SQLException {
        String query = "SELECT ID_CONF FROM games WHERE ID_GAME = (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            rs.next();
            return rs.getInt("ID_CONF");
        }
    }


    public Rectangle[] getInitialConfig(int game_id) throws SQLException {
        String query = "SELECT * FROM initial_state WHERE ID_CONF=" + game_id + ";";
        return getConfiguration(query);
    }

    public Rectangle[] getFinalConfig(String game_name) throws SQLException {
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




    public Vector<String> getGameList() throws SQLException {
        Vector<String> gameList = new Vector<>();
        String query = "SELECT name FROM games WHERE ID_USER=" + id_player + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next())
                gameList.add(rs.getString("name"));

        }
        return gameList;
    }

    public void deleteGame(String game_name) throws SQLException {

        String query = "CALL delete_game('" + game_name + "')";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }

    }

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

    public void closeConnection() throws SQLException{
        conn.close();
    }

    public boolean isLogged() {
        return id_player != -1;
    }

}




