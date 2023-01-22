package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String sqlQuery = "";

    public UserDaoJDBCImpl() {
        //
    }

    public void createUsersTable() {
        sqlQuery = "CREATE TABLE IF NOT EXISTS user ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(255), "
                + "lastName VARCHAR(255), "
                + "age INT)";

        Util.makeTransaction(sqlQuery);
    }

    public void dropUsersTable() {
        sqlQuery = "DROP TABLE IF EXISTS user;";

        Util.makeTransaction(sqlQuery);
    }

    public void saveUser(String name, String lastName, byte age) {

        sqlQuery = "INSERT into user ("
                // + "id, "
                + "name, "
                + "lastName, "
                + "age)"
                + "VALUES(?, ?, ?)";

        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                try (PreparedStatement st = conn.prepareStatement(sqlQuery)) {
                    st.setString(1, name);
                    st.setString(2, lastName);
                    st.setInt(3, age);
                    st.executeUpdate();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        sqlQuery = "DELETE FROM user WHERE id = ?";

        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                try (PreparedStatement st = conn.prepareStatement(sqlQuery)) {
                    st.setLong(1, id);
                    st.executeUpdate();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        sqlQuery = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                try (PreparedStatement st = conn.prepareStatement(sqlQuery)) {
                    ResultSet resp = st.executeQuery(sqlQuery);
                    while (resp.next()) {
                        User usr = new User(
                                resp.getString("name"),
                                resp.getString("lastName"),
                                resp.getByte("age"));
                        usr.setId(resp.getLong("id"));
                        users.add(usr);
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        this.dropUsersTable();
        this.createUsersTable();
    }
}
