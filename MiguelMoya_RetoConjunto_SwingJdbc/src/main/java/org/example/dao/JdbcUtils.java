package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

    public static Connection getConn() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/retoConjunto";
        String user = "root";
        String pass = System.getenv("MYSQL_ROOT_PASSWORD");

        return DriverManager.getConnection(url, user, pass);
    }
}
