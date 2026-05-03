package org.user_crud.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.user_crud.entity.User;
import org.user_crud.util.HibernateUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoImplIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("user_crud_test")
            .withUsername("test")
            .withPassword("test");

    private UserDaoImpl userDao;

    @BeforeAll
    void beforeAll() {
        POSTGRES.start();
        System.setProperty("test.db.url", POSTGRES.getJdbcUrl());
        System.setProperty("test.db.username", POSTGRES.getUsername());
        System.setProperty("test.db.password", POSTGRES.getPassword());
    }

    @AfterAll
    void afterAll() {
        HibernateUtil.getSessionFactory().close();
        POSTGRES.stop();
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        cleanTable();
    }

    @Test
    void saveAndFindByIdPersistsUser() {
        User user = new User("Harry", "harry@example.com", 30, LocalDateTime.now());

        userDao.save(user);
        User saved = userDao.findById((int) user.getId());

        assertNotNull(saved);
        assertEquals("Harry", saved.getName());
        assertEquals("harry@example.com", saved.getEmail());
        assertEquals(30, saved.getAge());
    }

    @Test
    void updateChangesPersistedUser() {
        User user = new User("Bob", "bob@example.com", 25, LocalDateTime.now());
        userDao.save(user);

        user.setName("Bobby");
        user.setEmail("bob@example.com");
        user.setAge(26);
        userDao.update(user);

        User updated = userDao.findById((int) user.getId());
        assertNotNull(updated);
        assertEquals("Jon", updated.getName());
        assertEquals("jon@example.com", updated.getEmail());
        assertEquals(26, updated.getAge());
    }

    @Test
    void deleteRemovesUser() {
        User user = new User("Jon", "jon@example.com", 28, LocalDateTime.now());
        userDao.save(user);

        userDao.delete((int) user.getId());

        User deleted = userDao.findById((int) user.getId());
        assertNull(deleted);
    }

    @Test
    void findByIdReturnsNullWhenUserDoesNotExist() {
        User user = userDao.findById(999_999);

        assertNull(user);
    }

    private void cleanTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
