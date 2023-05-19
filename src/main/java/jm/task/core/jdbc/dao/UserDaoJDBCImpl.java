package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    String create = """ 
CREATE TABLE IF NOT EXISTS users
(id INT PRIMARY KEY AUTO_INCREMENT,
 name TEXT, surname TEXT, age INT)
""";
    String drop = "DROP TABLE IF EXISTS users";

    String select = "SELECT * FROM users";

    String save = """
               INSERT INTO users (name, surname, age) values (?,?,? )
                """;
    String remove = "DELETE FROM users WHERE id = ?";

    String deleteTable = "DELETE FROM users";


    public void createUsersTable() {
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement()){

            statement.execute(create);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.connection();
        Statement statement = connection.createStatement()) {

            statement.execute(drop);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.connection();
            PreparedStatement statement = connection.prepareStatement(save)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        try(Connection connection = Util.connection();
            PreparedStatement statement = connection.prepareStatement(remove)) {

            statement.setLong(1,id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        try (Connection connection = Util.connection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(select);

            while (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("surname"));
                user.setAge(rs.getByte("age"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement()) {

            statement.execute(deleteTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
