package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getJDBCConnection();
    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    create table  if not exists users(
                    id serial,
                    firstname varchar(100) not NULL,
                    lastname varchar(100) not NULL,
                    age int not NULL);
                    """);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    DROP TABLE users
                    """);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(""" 
                    insert into users (firstname,lastname,age)
                    values (?,?,?)
                    """);
            {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
            }
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось добавить user");
        }

    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                           delete from users 
                           where id = ?
                    """);
            {
                preparedStatement.setLong(1, id);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить user");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("""
                    SELECT * FROM users
                    """);
            while (result.next()) {
                list.add(new User(
                        result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getByte(4)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Не удалось достать всех users");
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                                TRUNCATE TABLE users
                    """);
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу от users");
        }

    }
}
