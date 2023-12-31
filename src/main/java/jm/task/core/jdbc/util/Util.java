package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/kata1.2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    public static Connection getConnection() {

        try {
            if (connection == null || !connection.isClosed()) {
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                if (connection != null) {
                    System.out.println("Соединение с БД установленно");
                    connection.setAutoCommit(false);
                }
            } else {
                System.out.println("!!! Соединение уже было установленно ранее");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка connection" + "\n");
        }
        return connection;
    }

    public static void disconnectDB() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            } else {
                System.out.println("Соединение уже было закрыто ранее");
            }
        } catch (SQLException throwables) {
            System.err.println("Ошибка закрытия БД");
        }
    }

    public static void getRollback () {
        if (connection != null) {
            try {
                System.out.println("rollback");
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else { System.out.println("connection = null"); }
    }
}
