package org.user_crud.service;

import org.user_crud.dao.UserDao;
import org.user_crud.dao.UserDaoImpl;
import org.user_crud.entity.User;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        try {
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            return userDao.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("User not found", e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            userDao.update(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user", e);
        }

    }

    @Override
    public void deleteUser(int id) {
        try {
            userDao.delete(id);
        }  catch (Exception e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
