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
    private static final String TABLE_NAME = "users";
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (ID BIGINT PRIMARY KEY auto_increment, " +
                    "NAME varchar(30), " +
                    "LASTNAME varchar(30), " +
                    "AGE TINYINT)");

            System.out.println("Таблица создана\n");
            connection.commit();
        } catch (SQLException e) {
                Util.getRollback();
            System.err.println("Ошибка в создании таблицы\n");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
            System.out.println("Таблица удалена\n");

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы\n");
                Util.getRollback();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO " + TABLE_NAME + " ( name, lastName, age ) Values ( ?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User %s создан.\n", name);

            connection.commit();
        } catch (SQLException e) {
            System.err.printf("Создание %s не удалось \n ", name);
                Util.getRollback();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM " + TABLE_NAME + " where id = " + id)) {
            preparedStatement.executeUpdate();
            System.out.printf(" Строка с id %d удалена \n", id);

            connection.commit();
        } catch (SQLException e) {
            System.err.printf("Ошибка при удалении по id %d \n", id);
                Util.getRollback();
        }
    }

    public List<User> getAllUsers() {
        ResultSet resultSet;
        List<User> usersList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("LastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка в получении всех users\n");
                Util.getRollback();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE " + TABLE_NAME);
            System.out.printf("Таблица %s очищена \n", TABLE_NAME);

            connection.commit();
        } catch (SQLException e) {
            System.err.printf("Ошибка очистки таблицы %s \n", TABLE_NAME);
                Util.getRollback();
        }
    }
}
