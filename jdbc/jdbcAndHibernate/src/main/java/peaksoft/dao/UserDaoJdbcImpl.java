package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static peaksoft.util.Util.connect;

public class UserDaoJdbcImpl implements UserDao {

    public static final String ANSI_VIOLET = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    List<User> userList = new ArrayList<>();

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL," +
                    "age smallint NOT NULL)";
            Connection conn = connect();
            Statement statement = conn.createStatement();
            System.out.println(ANSI_BLUE + "Таблица users создана" + ANSI_RESET);
            statement.executeUpdate(SQL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            String SQL = "DROP TABLE IF EXISTS users";
            Connection conn = connect();
            Statement statement = conn.createStatement();
            System.out.println(ANSI_RED + "Таблица users удалена" + ANSI_RESET);
            statement.executeUpdate(SQL);
            userList.clear();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            System.out.println(ANSI_VIOLET + "Пользователь " + name + " добавлен" + ANSI_RESET);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
            System.out.println(ANSI_BLUE + "Пользователь удален id = " + id + ANSI_RESET);
        } catch (SQLException ex) {
            System.out.println("");
        }
    }

    public List<User> getAllUsers() {
        userList.clear();
        String SQL = "SELECT * FROM users";
        try (Connection conn = Util.connect();
             Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                User user = new User();
                long id = rs.getInt("id");
                String firstname = rs.getString("name");
                String lastname = rs.getString("lastName");
                int age = rs.getInt("age");
                user.setId(id);
                user.setName(firstname);
                user.setLastName(lastname);
                user.setAge((byte) age);
                userList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String SQLDelete = "TRUNCATE TABLE  users";
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(SQLDelete);
            userList.clear();
            System.out.println(ANSI_RED + "Таблица очищена" + ANSI_RESET);
        } catch (SQLException ex) {
            System.out.println("");
        }
    }
}