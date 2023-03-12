package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user(id, name, last_name, age);");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (Util.getSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSession().getTransaction().rollback();
            }
            throw new HibernateException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        try {
            session.getTransaction().begin();
            session.createSQLQuery("DROP TABLE IF EXISTS user;");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            throw new HibernateException(e);
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                System.out.println("Исключение при попытке закрытия сессии");
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        EntityManager entityManager = Util.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(new User (name, lastName, age));
            entityManager.getTransaction().commit();
            System.out.printf("User с именем – \s добавлен в базу данных \n", name);
        } catch (Exception e) {
            if (Util.getSession().getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || Util.getSession().getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                Util.getSession().getTransaction().rollback();
            }
            throw new HibernateException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
