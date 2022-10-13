package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySql {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        String connectionUrl =  "jdbc:mysql://localhost:3306/iptv";
        return DriverManager.getConnection(connectionUrl,"root","982504");
    }
}