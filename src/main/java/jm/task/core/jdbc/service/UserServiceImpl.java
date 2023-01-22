package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.*;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao dao = null;

    public UserServiceImpl() {
        dao = new UserDaoJDBCImpl();
    }

    public void createUsersTable() {
        dao.createUsersTable();
    }

    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        dao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> users = dao.getAllUsers();
        for (User usr : users) {
            System.out.println(usr);
        }
        return users;
    }

    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}
