package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
private static final String NAME = "root1";
private static final String PASSWORD = "root1";
private static final String URL = "jdbc:mysql://localhost:3306/new_db";





public static Connection connection() {

    Connection connection = null;

    try {
        connection = DriverManager.getConnection(URL, PASSWORD, NAME);

    } catch (SQLException e){
        throw new RuntimeException(e);
    }
    return connection;

}


}
