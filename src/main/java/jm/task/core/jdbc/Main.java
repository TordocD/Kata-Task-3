package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        //Cоздание таблицы user
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        //Добавление пользователей в user
        userService.saveUser("Dmitry", "Nedelko", (byte)22);
        userService.saveUser("Henry", "Gates", (byte)67);
        userService.saveUser("James", "Gosling", (byte)67);
        userService.saveUser("Pavel", "Durov", (byte)38);

        System.out.println("\nСписок пользователей после добавления:\n");
        //Получение всех пользователей из user
        for (int i = 0; i < 4; i++) {
            System.out.println(userService.getAllUsers().get(i).toString());
        }

        System.out.println("\nСписок пользователей после удаления пользователя с id = 2:\n");
        userService.removeUserById(2);

        for (int i = 0; i < 3; i++) {
            System.out.println(userService.getAllUsers().get(i).toString());
        }

        //Очистка таблицы user
        userService.cleanUsersTable();

        //Удаление таблицы user
        userService.dropUsersTable();
    }
}

