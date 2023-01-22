package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        final String hostName = "localhost";
        final String dbName = "katapp";
        final String userName = "master";
        final String password = "Admin192168";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
            String userName, String password) throws SQLException,
            ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }

    public static void makeTransaction(String query) {
        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                try (PreparedStatement st = conn.prepareStatement(query)) {
                    st.execute();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
