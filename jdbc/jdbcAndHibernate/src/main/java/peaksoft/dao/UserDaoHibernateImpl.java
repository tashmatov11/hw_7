package peaksoft.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public static final String ANSI_VIOLET = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.createSQLQuery("").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println(ANSI_BLUE + "Таблица users создана" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS users").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println(ANSI_BLUE + "Таблица users удалена" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            session.close();
            System.out.println(ANSI_VIOLET + "Пользователь " + name + " добавлен" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            String str = String.valueOf(id);
            Session session = Util.getSession().openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM  users WHERE id = " + str).executeUpdate();
            transaction.commit();
            System.out.println(ANSI_BLUE + "Пользователь удален id = " + id + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try {
            Session session = Util.getSession().openSession();
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User ").list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users").executeUpdate();
            session.getTransaction().commit();
            System.out.println(ANSI_BLUE + "Таблица очищена" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}