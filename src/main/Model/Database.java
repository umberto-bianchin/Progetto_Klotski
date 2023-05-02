package Model;

import java.awt.*;
import java.sql.*;
import java.util.LinkedList;

public class Database {

    private final Connection conn;
    private final String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
    private final String username = "admin";
    private final String password = "mypassword";

    public Database() throws Exception{
        //datbase connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(dbURL, username, password);
        System.out.println("Connected to database");

    }

    public boolean saveGame(LinkedList<Move> moves, int initial_config, Rectangle[] final_config, int id_game) throws SQLException{
        Statement stmt = conn.createStatement();
        String query = "";
        ResultSet rs = null;
        for(int i=0; i<moves.size(); i++){
            Move move = moves.get(i);
            query = "INSERT INTO saved_move(ID_GAME,width,height,x_ini,y_ini,x_fin,y_fin,ID_CONF) " +
                    "VALUES("+id_game+","+move.getInitialPosition().width+","+move.getInitialPosition().height+","
                    +move.getInitialPosition().x+","+move.getInitialPosition().y+","+move.getFinalPosition().x+","+move.getFinalPosition().y+","+initial_config+");";
            rs = stmt.executeQuery(query);
        }

        for(int i=0; i<final_config.length; i++){
            query = "INSERT INTO saved_state(ID_GAME, x,y,width,height) VALUES("+id_game+","+final_config[i].x+","+final_config[i].y+","+final_config[i].width+","+final_config[i].height+");";
            rs = stmt.executeQuery(query);
        }

        return true;
    }

    public LinkedList<Move> getSavedMoves(int id_game) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM saved_move WHERE ID_GAME="+id_game+";";
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

        return ret;
    }

    public int getIdConf(int id_game) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT ID_CONF FROM saved_move WHERE ID_GAME="+id_game+" ORDER BY ID_MOSSA LIMIT 1;";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getInt("ID_CONF");
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
        return temp;
    }


    public Rectangle[] getFinalConfig(int id_game) throws SQLException{
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM saved_state WHERE ID_GAME=" + id_game+";";
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
        return temp;
    }

    public boolean deleteGame(int id_game) {
        return false;
    }

}




