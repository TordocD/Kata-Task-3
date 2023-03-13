package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user(\n" +
            "id INT PRIMARY KEY AUTO_INCREMENT, \n" +
            "name VARCHAR(20), \n" +
            "last_name VARCHAR(20), \n" +
            "age INT);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user;";
    private static final String INSERT_USER = "INSERT INTO user(name, last_name, age) " +
            "VALUES(?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?;";
    private static final String CLEAR_TABLE = "TRUNCATE TABLE user";
    private static final String GET_USER = "SELECT * FROM user;";
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_TABLE);

        } catch (SQLException e) {
            System.err.println("Проблемы с соединением при создании таблицы");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DROP_TABLE);

        } catch (SQLException e) {
            System.err.println("Проблемы с соединением при удалении таблицы");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement prst = connection.prepareStatement(INSERT_USER)) {

            prst.setString(1, name);
            prst.setString(2, lastName);
            prst.setByte(3, age);
            prst.executeUpdate();

            System.out.printf("User с именем – \s добавлен в базу данных \n", name);

        } catch (SQLException e) {
            System.err.println("Проблемы с соединением при добавлении данных новых пользователей в таблицу");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement prst = connection.prepareStatement(DELETE_USER)) {
            prst.setLong(1, id);
            prst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Проблемы с соединением при удалении пользователя из таблицы");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(GET_USER);
            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Byte age = resultSet.getByte("age");

                users.add(new User(id, name, lastName, age));

            }
        } catch (SQLException e) {
            System.err.println("Проблемы с соединением при получении данных пользователей из таблицы");
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(CLEAR_TABLE);

        } catch (SQLException e) {
            System.err.println("Проблемы с соединением при очистке таблицы");
            e.printStackTrace();
        }
    }
}
