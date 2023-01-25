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
    public UserDaoJDBCImpl() {
        //
    }

    public void createUsersTable() {
        Util.makeTransaction("CREATE TABLE IF NOT EXISTS user ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(255), "
                + "lastName VARCHAR(255), "
                + "age INT)");
    }

    public void dropUsersTable() {
        Util.makeTransaction("DROP TABLE IF EXISTS user;");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                conn.setAutoCommit(false);
                try (PreparedStatement st = conn.prepareStatement(
                        "INSERT into user (" + "name, " + "lastName, " + "age)" + "VALUES(?, ?, ?)")) {
                    st.setString(1, name);
                    st.setString(2, lastName);
                    st.setInt(3, age);
                    st.executeUpdate();
                    conn.commit();
                } catch (SQLException se) {
                    conn.rollback();
                    se.printStackTrace();
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                try (PreparedStatement st = conn.prepareStatement("DELETE FROM user WHERE id = ?")) {
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
        List<User> users = new ArrayList<>();

        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                try (PreparedStatement st = conn.prepareStatement("SELECT * FROM user")) {
                    ResultSet resp = st.executeQuery("SELECT * FROM user");
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
        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                conn.setAutoCommit(false);
                try (PreparedStatement st = conn.prepareStatement("DELETE FROM user")) {
                    st.executeUpdate();
                    conn.commit();
                } catch (SQLException se) {
                    se.printStackTrace();
                    conn.rollback();
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
