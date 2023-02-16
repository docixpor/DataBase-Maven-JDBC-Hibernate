package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Anton", "Antonov", (byte) 22);
        userService.saveUser("Maxim", "Maximov", (byte) 26);
        userService.saveUser("Egor", "Egorov", (byte) 32);
        userService.saveUser("Alex", "Alexeev", (byte) 42);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
