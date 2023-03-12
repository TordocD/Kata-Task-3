package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        //Cоздание таблицы user
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        //Добавление пользователей в user
        userService.saveUser("Dmitry", "Nedelko", (byte)22);
        userService.saveUser("Henry", "Gates", (byte)67);
        userService.saveUser("James", "Gosling", (byte)67);
        userService.saveUser("Pavel", "Durov", (byte)38);

        //Получение всех пользователей из user
        for (int i = 0; i < 4; i++) {
            System.out.println(userService.getAllUsers().get(i).toString());
        }

        //Очистка таблицы user
        userService.cleanUsersTable();

        //Удаление таблицы user
        userService.dropUsersTable();
    }
}
