package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
          UserService userService = new UserServiceImpl();
          userService.createUsersTable();
        userService.saveUser("Дима","Пупкин", (byte) 24);
        userService.saveUser("Олег","Картошкин", (byte) 18);
        userService.saveUser("Антон","Пердежин", (byte) 21);
//        userService.saveUser("Один","Богов", (byte) 99);
//        userService.getAllUsers().forEach(System.out::println);
//        userService.dropUsersTable();
//          userService.removeUserById(1);
    }
}
