package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sql = """
    CREATE TABLE IF NOT EXISTS users (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        lastName VARCHAR(255) NOT NULL,
        age TINYINT NOT NULL
    );
""";
        try(
                Connection connection = Util.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            if (!stmt.execute(sql)) {
                System.out.println("crate user table");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";
        try(
                Connection connection = Util.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            if (!stmt.execute(sql)) {
                System.out.println("drop user table");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?);";
        try(
                Connection connection = Util.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?;";
        try(
                Connection connection = Util.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try(
                Connection connection = Util.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
                System.out.println(user);
            }

            return users;
            // User с именем — name добавлен в базу данных
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users;";
        try(
                Connection connection = Util.getConnection();
                Statement stmt = connection.createStatement()
        ) {
            if (!stmt.execute(sql)) {
                System.out.println("clear user table");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
