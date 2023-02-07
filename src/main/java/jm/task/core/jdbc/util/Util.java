package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

// import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jm.task.core.jdbc.model.User;

public class Util {

    public static SessionFactory getHibernateSession() throws SQLException, ClassNotFoundException {
        final String hostName = "localhost";
        final String dbName = "katapp";
        final String userName = "master";
        final String password = "Admin192168";

        return getHibernateSession(hostName, dbName, userName, password);
    }

    public static SessionFactory getHibernateSession(String hostName, String dbName, String userName, String password) {
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://" + hostName + ":3306/" + dbName);
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.username", userName);
        prop.setProperty("hibernate.connection.password", password);
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        // prop.setProperty("hibernate.hbm2ddl.auto", "create");
        // prop.setProperty("show_sql", "true");
        SessionFactory sf = null;
        try {
            Configuration conf = new Configuration().addProperties(prop).addAnnotatedClass(User.class);
            sf = conf.buildSessionFactory();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sf;
    }

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
        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }

    public static void makeTransaction(String query) {
        try (Connection conn = Util.getMySQLConnection()) {
            if (conn != null) {
                // conn.setAutoCommit(false);
                try (PreparedStatement st = conn.prepareStatement(query)) {
                    st.execute();
                    // conn.commit();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
