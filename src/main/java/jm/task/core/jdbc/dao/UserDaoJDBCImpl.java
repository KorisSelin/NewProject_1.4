package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/kata1.2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String TABLE_NAME = "users";
    private static int count;

    public UserDaoJDBCImpl() {
         connection = Util.connectDB();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE " + TABLE_NAME +
                    " (ID INT PRIMARY KEY auto_increment, " +
                    "NAME varchar(30), " +
                    "LASTNAME varchar(30), " +
                    "AGE INT)");
            System.out.println("Таблица создана\n");
        } catch (SQLException e) {
            System.err.println("Ошибка в создании таблици\n");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);
            System.out.println("Таблица удалена\n");
        } catch (SQLException e){
            System.err.println("Ошибка удаления таблицы\n");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO " + TABLE_NAME + " ( name, lastName, age ) Values ( ?, ?, ?)")) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,age);
            count += preparedStatement.executeUpdate();

            System.out.printf("User %s создан.\n", name);
        } catch ( SQLException e){
            System.err.printf("Создание %s не удалось \n ", name);
        }
    }

    public void removeUserById(long id) {
        try ( PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM " + TABLE_NAME + " where id = " + id)){
            preparedStatement.execute("DELETE FROM " + TABLE_NAME + " where id = " + id);
        System.out.printf(" Строка с id %d удалена \n",id);

        } catch (SQLException e){
            System.err.printf( "Ошибка при удалении по id %d \n", id);
        }
    }

    public List<User> getAllUsers() {
        ResultSet resultSet;
        List <User> usersList = new ArrayList();
        try (Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                    resultSet.getString("LastName"),
                    resultSet.getByte("age"));
                    user.setId(resultSet.getLong("id"));
                    usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в получении всех users\n");
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try ( Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE " + TABLE_NAME);
            System.out.printf("Таблица %s очищена \n", TABLE_NAME);
        } catch (SQLException e){
            System.err.printf("Ошибка очистки таблицы %s \n", TABLE_NAME);
        }
    }
}
