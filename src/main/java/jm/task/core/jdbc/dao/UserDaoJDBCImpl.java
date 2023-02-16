package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE Users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(80), lastName VARCHAR(80), age BIGINT)"))
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {}
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE Users"))
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {}
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(name, lastName, age) VALUES (?, ?, ?);"))
        {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE id = ?"))
        {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users"))
        {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);

                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users"))
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
