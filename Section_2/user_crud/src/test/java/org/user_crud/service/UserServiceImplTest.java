package org.user_crud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user_crud.dao.UserDao;
import org.user_crud.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserDelegatesToDao() {
        User user = buildUser();

        userService.createUser(user);

        verify(userDao).save(user);
    }

    @Test
    void createUserWrapsDaoException() {
        User user = buildUser();
        doThrow(new RuntimeException("db error")).when(userDao).save(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(user));

        assertEquals("Failed to create user", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void getUserByIdReturnsUser() {
        User expected = buildUser();
        expected.setId(1L);
        when(userDao.findById(1)).thenReturn(expected);

        User actual = userService.getUserById(1);

        assertEquals(expected, actual);
        verify(userDao).findById(1);
    }

    @Test
    void getUserByIdWrapsDaoException() {
        when(userDao.findById(1)).thenThrow(new RuntimeException("db error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(1));

        assertEquals("User not found", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void updateUserDelegatesToDao() {
        User user = buildUser();
        user.setId(2L);

        userService.updateUser(user);

        verify(userDao).update(user);
    }

    @Test
    void updateUserWrapsDaoException() {
        User user = buildUser();
        doThrow(new RuntimeException("db error")).when(userDao).update(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(user));

        assertEquals("Failed to update user", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void deleteUserDelegatesToDao() {
        userService.deleteUser(5);

        verify(userDao).delete(5);
    }

    @Test
    void deleteUserWrapsDaoException() {
        doThrow(new RuntimeException("db error")).when(userDao).delete(5);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(5));

        assertEquals("Failed to delete user", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    private User buildUser() {
        return new User("Jon", "jon@example.com", 30, LocalDateTime.now());
    }
}
