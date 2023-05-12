package Model;

import java.awt.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Vector;


public class Database {

    private Connection conn;
    private final String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
    private final String username = "admin";
    private final String password = "mypassword";
    private int id_player = -1;

    public Database() throws Exception{
        //database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, username, password);

    }

    public boolean saveGame(LinkedList<Move> moves, int initial_config, Rectangle[] final_config, String game_name) throws SQLException{
        Statement stmt = conn.createStatement();
        String query = "SELECT ID_GAME FROM games WHERE name = '"+game_name+"' AND ID_USER ="+id_player+";";
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()) {
            stmt.close();
            rs.close();
            return false;
        }else {
            query = "INSERT INTO games (ID_USER,ID_CONF, name) VALUES (" + id_player + ","+initial_config+",'" + game_name + "');"; //inserisce il game (autoincrementa)
            stmt.execute(query);
            query = "SELECT ID_GAME FROM games WHERE ID_USER = " + id_player + " ORDER BY ID_GAME DESC LIMIT 1;"; //prende l'id del game inserito
            rs = stmt.executeQuery(query);
            rs.next();
            int id_game = rs.getInt("ID_GAME");
            for (int i = 0; i < moves.size(); i++) {
                Move move = moves.get(i);
                query = "INSERT INTO saved_move(ID_GAME,ID_USER,width,height,x_ini,y_ini,x_fin,y_fin) " +
                        "VALUES(" + id_game + "," + id_player + "," + move.getInitialPosition().width + "," + move.getInitialPosition().height + ","
                        + move.getInitialPosition().x + "," + move.getInitialPosition().y + "," + move.getFinalPosition().x + "," + move.getFinalPosition().y + ");";
                stmt.execute(query);
            }

            for (int i = 0; i < final_config.length; i++) {
                query = "INSERT INTO saved_state(ID_GAME, ID_USER, x,y,width,height) VALUES(" + id_game + ", " + id_player + ", " + final_config[i].x + "," + final_config[i].y + "," + final_config[i].width + "," + final_config[i].height + ");";
                stmt.execute(query);
            }

            rs.close();
            stmt.close();
            return true;
        }
    }

    public LinkedList<Move> getSavedMoves(String game_name) throws SQLException {
        Statement stmt = conn.createStatement();
        /*
        String query = "SELECT ID_GAME FROM games WHERE name = '"+game_name+"';";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id_game = rs.getInt("ID_GAME");
        query = "SELECT * FROM saved_move WHERE ID_GAME=" + id_game + " AND ID_USER=" + id_player + ";";
        rs = stmt.executeQuery(query);
        */
        String query = "SELECT * FROM saved_move WHERE ID_GAME IN (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";
        ResultSet rs = stmt.executeQuery(query);
        LinkedList<Move> ret = new LinkedList<>();

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
        rs.close();
        stmt.close();
        return ret;
    }



    public int getIdConf(String game_name) throws SQLException {
        Statement stmt = conn.createStatement();
        /*
        String query = "SELECT ID_GAME FROM games WHERE name = '"+game_name+"';";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id_game = rs.getInt("ID_GAME");
        query = "SELECT ID_CONF FROM games WHERE ID_GAME=" + id_game + " AND ID_USER=" + id_player + ";";
        rs = stmt.executeQuery(query);
        rs.next();
        */
        String query = "SELECT ID_CONF FROM games WHERE ID_GAME = (SELECT ID_GAME FROM games WHERE name = '"+game_name+"') AND ID_USER = "+id_player+";";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id_conf = rs.getInt("ID_CONF");
        rs.close();
        stmt.close();


        return id_conf;

    }


    public Rectangle[] getInitialConfig(int game_id) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM initial_state WHERE ID_CONF=" + game_id + ";";
        ResultSet rs = stmt.executeQuery(query);
        Rectangle[] temp = new Rectangle[10];
        int count = 0;
        while (rs.next()) {
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int width = rs.getInt("width");
            int height = rs.getInt("height");
            temp[count] = new Rectangle(x, y, width, height);
            count++;
        }
        rs.close();
        stmt.close();
        return temp;
    }
    public Vector<String> getGameList() throws SQLException {
        Vector<String> gameList = new Vector<>();
        Statement stmt = conn.createStatement();
        String query = "SELECT name FROM games WHERE ID_USER=" + id_player + ";";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            gameList.add(rs.getString("name"));
        }
        rs.close();
        stmt.close();
        return gameList;
    }

    public Rectangle[] getFinalConfig(String game_name) throws SQLException {
        Statement stmt = conn.createStatement();
        /*
        String query = "SELECT ID_GAME FROM games WHERE name = '"+game_name+"';";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id_game = rs.getInt("ID_GAME");
        query = "SELECT * FROM saved_state WHERE ID_GAME=" + id_game + " AND ID_USER=" + id_player + ";";
        rs = stmt.executeQuery(query);
        */
        String query = "SELECT * FROM saved_state WHERE ID_GAME IN (SELECT ID_GAME FROM games WHERE name = '" + game_name + "') AND ID_USER = " + id_player + ";";
        ResultSet rs = stmt.executeQuery(query);
        Rectangle[] temp = new Rectangle[10];
        int count = 0;
        while (rs.next()) {
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int width = rs.getInt("width");
            int height = rs.getInt("height");
            temp[count] = new Rectangle(x, y, width, height);
            count++;
        }
        rs.close();
        stmt.close();
        return temp;
    }

    public void deleteGame(String game_name) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT ID_GAME FROM games WHERE name = '"+game_name+"';";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int id_game = rs.getInt("ID_GAME");
        query="DELETE FROM saved_state WHERE ID_GAME="+id_game+";";
        stmt.execute(query);

        query = "DELETE FROM saved_move WHERE ID_GAME =" + id_game + ";";
        stmt.execute(query);

        query = "DELETE FROM games WHERE ID_GAME =" + id_game + ";";
        stmt.execute(query);
        stmt.close();
    }

    public boolean login(String username, String password) throws SQLException {
        Statement stmt = conn.createStatement();
        int pass = password.hashCode();
        String query = "SELECT ID_USER FROM users WHERE username='" + username + "' AND password=" + pass + ";";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int user = username.hashCode();
            int id = user+pass;
            id_player = id;
            rs.close();
            stmt.close();
            return true;
        } else {
            rs.close();
            stmt.close();
            return false;
        }
    }

    public boolean registration(String username, String password) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT ID_USER FROM users WHERE username =  '" + username + "';";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            rs.close();
            stmt.close();
            return false; //if username already has been used
        } else {
            int user = username.hashCode();
            int pass = password.hashCode();
            int id = user + pass;
            id_player = id;
            query = "INSERT INTO users (ID_USER,username,password) values (" + id + ",'" + username + "'," + pass + ");";
            stmt.execute(query);
            rs.close();
            stmt.close();
            return true;
        }
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


}




