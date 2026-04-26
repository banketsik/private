package org.user_crud.dao;

import org.user_crud.entity.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    User findById(int id);
    void  update(User user);
    void delete(int id);
}