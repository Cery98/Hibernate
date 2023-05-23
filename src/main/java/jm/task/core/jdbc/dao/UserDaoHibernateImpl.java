package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private String create = """ 
            CREATE TABLE IF NOT EXISTS users
            (id INT PRIMARY KEY AUTO_INCREMENT,
             name TEXT, surname TEXT, age INT)
            """;
    private String drop = "DROP TABLE IF EXISTS users";
    private String deleteTable = "TRUNCATE users";


    private static SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery(create).executeUpdate();
            session.getTransaction().commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            session.getTransaction().commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();

        } catch (RuntimeException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery(deleteTable).executeUpdate();
            session.getTransaction().commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
