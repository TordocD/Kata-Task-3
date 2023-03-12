package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static String dbURL;
    private static String dbUsername;
    private static String dbPassword;
    private static SessionFactory sessionFactory = null;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .loadProperties("src/main/resources/hibernate.properties").build();
            try {
                sessionFactory = new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(User.class)
                        .buildMetadata()
                        .buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Исключние при создании sessionFactory");
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
                e.printStackTrace();
            }


        }
        return sessionFactory;
    }

    public static Session getSession() throws HibernateException {
        return getSessionFactory().openSession();
    }

    public static EntityManager getEntityManager() {
        return getSessionFactory().createEntityManager();
    }


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties dbProperties = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            dbProperties.load(fis);
            dbURL = dbProperties.getProperty("db.host");
            dbUsername = dbProperties.getProperty("db.userName");
            dbPassword = dbProperties.getProperty("db.password");
        } catch (FileNotFoundException e) {
            System.out.println("File config.properties not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }

    public static Connection getConnection(String dbHost, String dbName, String dbUsername, String dbPassword)
            throws SQLException {

        String dbURL = "jdbc:mysql//" + dbHost + ":3306/" + dbName;
        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }
}