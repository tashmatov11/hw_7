package peaksoft.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import peaksoft.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:postgresql://localhost:5432/hw_7";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "qwe123";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static final SessionFactory session = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }catch (Throwable ex){
            System.out.println("Session not create : " + ex);

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSession(){
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static void shutBown(){
        getSession().close();
    }
}