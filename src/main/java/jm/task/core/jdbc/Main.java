package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

//        UserService userService = new UserServiceImpl();
//        userService.createUsersTable();
//        userService.saveUser("Чапай", "Волков", (byte) 11);
//        userService.saveUser("Иван", "Иванович", (byte) 22);
//        userService.saveUser("Джим", "Кери", (byte) 33);
//        userService.saveUser("Дональд", "Трамп", (byte) 44);
//        for(User user : userService.getAllUsers()){
//            System.out.println(user);
//        }
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
//


        Util.disconnectDB();
    }
}
