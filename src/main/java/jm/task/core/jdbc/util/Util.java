package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    //     реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static SessionFactory sessionFactory;

    public static Session getSession() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
            sessionFactory.getCurrentSession();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        }
        return sessionFactory.openSession();
    }



    private static Connection connection;

    public static Connection getJDBCConnection() {

        {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
