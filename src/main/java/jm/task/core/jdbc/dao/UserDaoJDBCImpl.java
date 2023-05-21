package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    private String create = """ 
            CREATE TABLE IF NOT EXISTS users
            (id INT PRIMARY KEY AUTO_INCREMENT,
             name TEXT, surname TEXT, age INT)
            """;
    private String drop = "DROP TABLE IF EXISTS users";
    private String save = """
            INSERT INTO users (name, surname, age) values (?,?,? )
             """;
    private String remove = "DELETE FROM users WHERE id = ?";

    private String select = "SELECT id, name, surname, age FROM users";
    private String deleteTable = "TRUNCATE users";
    private static Connection connection = Util.connection();


    public void createUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(create)) {
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(drop)) {
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(select)) {

            ResultSet rs = preparedStatement.executeQuery(select);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("surname"));
                user.setAge(rs.getByte("age"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteTable)) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
