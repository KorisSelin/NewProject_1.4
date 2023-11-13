//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserDaoJDBCImpl implements UserDao {
//    private static Connection connection;
//    private static final String URL = "jdbc:mysql://localhost:3306/kata1.2";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "root";
//    private static final String TABLE_NAME = "users";
//    private static int count;
//
//    public UserDaoJDBCImpl() {
//         connection = Util.connectDB();
//    }
//
//    public void createUsersTable() {
//
//        String sql = "CREATE TABLE " + TABLE_NAME + " (ID INT PRIMARY KEY auto_increment, NAME varchar(30), LASTNAME varchar(30), AGE INT)";
//        try (Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//            System.out.println("Таблица создана\n");
//        } catch (SQLException e) {
//            System.err.println("Ошибка в создании таблици\n");
//        }
//
//    }
//
//    public void dropUsersTable() {
//        String sql = "DROP TABLE " + TABLE_NAME;
//        try (Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//            System.out.println("Таблица удалена\n");
//        } catch (SQLException e){
//            System.err.println("Ошибка удаления таблицы\n");
//        }
//    }
//
//    public void saveUser(String name, String lastName, byte age) {
//        String sql = "INSERT INTO " + TABLE_NAME + " ( name, lastName, age ) Values ( ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1,name);
//            preparedStatement.setString(2,lastName);
//            preparedStatement.setInt(3,age);
//            count += preparedStatement.executeUpdate();
//
//
//            System.out.printf("User %s создан.\n", name);
//        } catch ( SQLException e){
//            System.err.printf("Создание %s не удалось \n ", name);
//        }
//    }
//
//    public void removeUserById(long id) {
//        String sql = "DELETE FROM " + TABLE_NAME + " where id = " + id;
//        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)){
//            preparedStatement.execute(sql);
//        System.out.printf(" Строка с id %d удалена \n",id);
//
//        } catch (SQLException e){
//            System.err.printf( "Ошибка при удалении по id %d \n", id);
//        }
//    }
//
//    public List<User> getAllUsers() {
//        ResultSet resultSet;
//        String sql = "SELECT * FROM " + TABLE_NAME ;
//        List <User> usersList = new ArrayList();
//        try (Statement statement = connection.createStatement()){
//            resultSet = statement.executeQuery(sql);
//            while (resultSet.next()){
//                User user = new User(resultSet.getString("name"),
//                    resultSet.getString("LastName"),
//                    resultSet.getByte("age"));
//                    user.setId(resultSet.getLong("id"));
//                    usersList.add(user);
//            }
//        } catch (SQLException e) {
//            System.out.println("Ошибка в получении всех users\n");
//        }
//        return usersList;
//    }
//
//    public void cleanUsersTable() {
//        String sql = "DELETE FROM " + TABLE_NAME ;
//        try ( Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//            System.out.printf("Таблица %s очищена \n", TABLE_NAME);
//        } catch (SQLException e){
//            System.err.printf("Ошибка очистки таблицы %s \n", TABLE_NAME);
//        }
//
//    }
//}
