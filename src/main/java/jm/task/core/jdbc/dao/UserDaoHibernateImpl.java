package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSession();

        try {
            session.beginTransaction();
            session
                    .createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
                            "id INT," +
                            " name VARCHAR(20)," +
                            " last_name VARCHAR(20)," +
                            " age INT);").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            System.out.println("Исключение при попытке создания таблицы user");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();

        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user;").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            System.out.println("Исключение при попытке удаления таблицы user");
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();

        try {
            session.beginTransaction();
            session.persist(new User (name, lastName, age));
            session.getTransaction().commit();
            System.out.printf("User с именем – \s добавлен в базу данных \n", name);
        } catch (HibernateException e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            System.out.println("Исключение при попытке добавления пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();

        try {
            session.beginTransaction();
            session.createQuery("DELETE User WHERE id = :param").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            System.out.println("Исключение при попытке удаления пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            System.out.println("Исключение при попытке получения списка пользователей");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            System.out.println("Исключение при попытке очистки таблицы пользователей");
        }
    }
}
