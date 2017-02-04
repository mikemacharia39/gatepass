package database;
import java.sql.*;

import dialogs.ErrorMessage;
public class DBConnect {

	public static Connection conn;
    public static Statement stmt;

    public DBConnect() {//constructor
        connect();
    }

    public static void connect() {
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //localhost=127.0.0.1
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gatepass", "root", "");
            System.out.println("Connection established...");
            stmt = conn.createStatement();
        }
        catch (SQLException e) {
        	ErrorMessage.display("Database error", e.getMessage()+" \nError occured");
            e.printStackTrace();
        }
        catch (Exception e) {
        	ErrorMessage.display("Error", e.getMessage()+" \nError occured");
            e.printStackTrace();
        }

    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }	
}
