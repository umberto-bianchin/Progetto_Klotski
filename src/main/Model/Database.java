package Model;

import java.awt.*;
import java.sql.*;
import java.util.LinkedList;

public class Database {

    private final Connection conn;
    private final String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
    private final String username = "admin";
    private final String password = "mypassword";
    private int id_player = -1;

    public Database() {
        //datbase connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, username, password);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connected to database");

    }

    public boolean saveGame(LinkedList<Move> moves, int initial_config, Rectangle[] final_config, int id_game) throws SQLException{
        Statement stmt = conn.createStatement();
        String query = "";
        ResultSet rs = null;
        for(int i=0; i<moves.size(); i++){
            Move move = moves.get(i);
            query = "INSERT INTO saved_move(ID_GAME,ID_CONF,ID_USER,width,height,x_ini,y_ini,x_fin,y_fin) " +
                    "VALUES("+id_game+","+initial_config+","+id_player+","+move.getInitialPosition().width+","+move.getInitialPosition().height+","
                    +move.getInitialPosition().x+","+move.getInitialPosition().y+","+move.getFinalPosition().x+","+move.getFinalPosition().y+");";
            rs = stmt.executeQuery(query);
        }

        for(int i=0; i<final_config.length; i++){
            query = "INSERT INTO saved_state(ID_GAME, x,y,width,height) VALUES("+id_game+","+final_config[i].x+","+final_config[i].y+","+final_config[i].width+","+final_config[i].height+");";
            rs = stmt.executeQuery(query);
        }

        rs.close();
        stmt.close();
        return true;
    }

    public LinkedList<Move> getSavedMoves(int id_game) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM saved_move WHERE ID_GAME="+id_game+" AND ID_USER="+id_player+";";
        ResultSet rs = stmt.executeQuery(query);
        LinkedList<Move> ret = new LinkedList<Move>();

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

    public int getIdConf(int id_game) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT ID_CONF FROM saved_move WHERE ID_GAME="+id_game+" AND ID_USER="+id_player+" ORDER BY ID_MOSSA LIMIT 1;";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();

        int id_conf = rs.getInt("ID_CONF");

        rs.close();
        stmt.close();

        return id_conf;

    }


    public Rectangle[] getInitialConfig(int id_conf) throws SQLException{
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM initial_state WHERE ID_CONF="+id_conf+";";
        ResultSet rs = stmt.executeQuery(query);
        Rectangle[]temp = new Rectangle[10];
        int count = 0;
        while(rs.next()){
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int width = rs.getInt("width");
            int height = rs.getInt("height");
            temp[count]= new Rectangle(x, y, width, height);
            count++;
        }

        rs.close();
        stmt.close();
        return temp;
    }


    public Rectangle[] getFinalConfig(int id_game) throws SQLException{
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM saved_state WHERE ID_GAME=" + id_game+" AND ID_USER="+id_player+";";
        ResultSet rs = stmt.executeQuery(query);
        Rectangle[]temp = new Rectangle[10];
        int count = 0;
        while(rs.next()){
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int width = rs.getInt("width");
            int height = rs.getInt("height");
            temp[count]= new Rectangle(x, y, width, height);
            count++;
        }

        rs.close();
        stmt.close();
        return temp;
    }

    public boolean deleteGame(int id_game) {

        return false;
    }

    public boolean login(String username, String password){
        int pass = password.hashCode();

        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT ID_USER FROM users WHERE username="+username+" AND password="+pass+";";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int id_user = rs.getInt("ID_USER");
            rs.close();
            stmt.close();
            id_player = id_user;
            return true;

        } catch (SQLException e) {
            return false;
        }

    }

    public boolean registration(String username, String password) {
        int user = username.hashCode();
        int pass = password.hashCode();
        int id = user+pass;

        try {
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO users (ID_USER,username,password) values ("+id+","+username+","+pass+");";
            ResultSet rs = stmt.executeQuery(query);
            rs.close();
            stmt.close();
            id_player = id;
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public void setIdPlayer(){
        //logout
        id_player = -1;
    }


    public void databaseClose() throws SQLException {
        conn.close();
    }


}




