package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getHibernateSession().openSession()) {
            Transaction trans = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS User "
                    + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(150), "
                    + "lastName VARCHAR(150), "
                    + "age INT);", String.class).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getHibernateSession().openSession()) {
            if (session != null) {
                Transaction trans = session.beginTransaction();
                session.createNativeQuery("DROP TABLE IF EXISTS User;", String.class).executeUpdate();
                trans.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User usr = new User(name, lastName, age);
        try (Session session = Util.getHibernateSession().openSession()) {
            Transaction trans = session.beginTransaction();
            session.persist(usr);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getHibernateSession().openSession()) {
            Transaction trans = session.beginTransaction();
            User usr = session.get(User.class, id);
            session.remove(usr);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getHibernateSession().openSession()) {
            users = session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getHibernateSession().openSession()) {
            Transaction trans = session.beginTransaction();
            session.createNativeQuery("delete from User",Boolean.class).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
