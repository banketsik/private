package org.user_crud.service;

import org.user_crud.entity.User;

public interface UserService {
    void createUser(User user);
    User getUserById(int id);
    void updateUser(User user);
    void deleteUser(int id);
}
