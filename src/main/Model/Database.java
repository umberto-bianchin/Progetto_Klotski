package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Database() {
        String dbURL = "jdbc:mysql://progettoklotski.c6i3tfhv1iee.eu-north-1.rds.amazonaws.com:3306/progettoklotski";
        String username = "admin";
        String password = "mypassword";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to database");
            // Do something with the database connection
            
            //conn.close();
            //System.out.println("Disconnected from database");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[]args){

        Database db = new Database();
    }
}




