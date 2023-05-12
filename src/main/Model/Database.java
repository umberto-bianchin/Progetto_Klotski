package Model;

import javax.xml.transform.Result;
import java.awt.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Vector;
import java.sql.CallableStatement;
import java.sql.Types;



public class Database {

    private Connection conn;
    private final String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
    private final String username = "admin";
    private final String password = "mypassword";
    private int id_player = -1;

    private Statement stmt = null;
    private ResultSet rs = null;

    public Database() throws Exception{
        //database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(dbURL, username, password);

    }

    public boolean saveGame(LinkedList<Move> moves, int initial_config, Rectangle[] final_config, String game_name){
        try{
            stmt = conn.createStatement();
            String query = "SELECT ID_GAME FROM games WHERE name = '"+game_name+"' AND ID_USER ="+id_player+";";
            rs = stmt.executeQuery(query);
            if(rs.next()) {
                stmt.close();
                rs.close();
                return false;
            }else {

                query = "INSERT INTO games (ID_USER,ID_CONF, name) VALUES (" + id_player + "," + initial_config + ",'" + game_name + "');"; //inserisce il game (autoincrementa)
                stmt.execute(query);
                query = "SELECT ID_GAME FROM games WHERE ID_USER = " + id_player + " ORDER BY ID_GAME DESC LIMIT 1;"; //prende l'id del game inserito
                rs = stmt.executeQuery(query);
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
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            stmtRsClose();
        }
        return true;
    }

    public LinkedList<Move> getSavedMoves(String game_name){
        LinkedList<Move> ret = new LinkedList<>();
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM saved_move WHERE ID_GAME IN (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int x_ini = rs.getInt("x_ini");
                int y_ini = rs.getInt("y_ini");
                int x_fin = rs.getInt("x_fin");
                int y_fin = rs.getInt("y_fin");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                Move temp = new Move(new Rectangle(x_ini, y_ini, width, height), new Rectangle(x_fin, y_fin, width, height));
                ret.addLast(temp);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            stmtRsClose();
        }
        return ret;
    }



    public int getIdConf(String game_name) {
        int id_conf = 0;
        try {
            stmt = conn.createStatement();
            String query = "SELECT ID_CONF FROM games WHERE ID_GAME = (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";
            rs = stmt.executeQuery(query);
            rs.next();
            id_conf = rs.getInt("ID_CONF");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            stmtRsClose();
        }

        return id_conf;

    }


    public Rectangle[] getInitialConfig(int game_id){
        Rectangle[] temp = new Rectangle[10];
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM initial_state WHERE ID_CONF=" + game_id + ";";
            rs = stmt.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                temp[count] = new Rectangle(x, y, width, height);
                count++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            stmtRsClose();
        }
        return temp;
    }
    public Vector<String> getGameList(){
        Vector<String> gameList = new Vector<>();
        try {
            stmt = conn.createStatement();
            String query = "SELECT name FROM games WHERE ID_USER=" + id_player + ";";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                gameList.add(rs.getString("name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            stmtRsClose();
        }
        return gameList;
    }

    public Rectangle[] getFinalConfig(String game_name){
        Rectangle[] temp = new Rectangle[10];
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM saved_state WHERE ID_GAME IN (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";
            rs = stmt.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                temp[count] = new Rectangle(x, y, width, height);
                count++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            stmtRsClose();
        }
        return temp;
    }

    public void deleteGame(String game_name) {
        try {
            stmt = conn.createStatement();
            String deleteQuery = "CALL delete_game('" + game_name + "')";
            stmt.execute(deleteQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            stmtRsClose();
        }
    }

    public boolean login(String username, String password){
        try {
            stmt = conn.createStatement();
            int pass = password.hashCode();
            String query = "SELECT ID_USER FROM users WHERE username='" + username + "' AND password=" + pass + ";";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                int user = username.hashCode();
                id_player = user + pass;
                stmtRsClose();
                return true;
            } else {
                stmtRsClose();
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            stmtRsClose();
        }
        return false;
    }

    public boolean registration(String username, String password){
        try {

            stmt = conn.createStatement();
            int user = username.hashCode();
            int pass = password.hashCode();
            int id = user + pass;

            String query = "CALL insert_user(?,?,?,?);";
            CallableStatement cstmt = conn.prepareCall(query);
            cstmt.setInt(1, id);
            cstmt.setString(2, username);
            cstmt.setInt(3, pass);
            cstmt.registerOutParameter(4, Types.INTEGER);
            cstmt.execute();

            int rowsAffected = cstmt.getInt(4);
            if (rowsAffected == 1) {
                stmtRsClose();
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            stmtRsClose();
        }
        return false;
    }

    public void resetIdPlayer() {
        //logout
        id_player = -1;
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public boolean isLogged(){
        return id_player != -1;
    }

    public void stmtRsClose(){
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




