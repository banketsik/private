package org.user_crud.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user_crud.entity.User;
import org.user_crud.util.HibernateUtil;


public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void save(User user) {
        Transaction transaction =  null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            logger.info("Saving user: {}", user.getName());
            session.persist(user);
            transaction.commit();
            logger.info("User saved successfully with id: {}", user.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error saving user: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Finding user by id {}", id);
            User user = session.get(User.class, id);
            if (user != null) {
                logger.info("User with id was found: {}", id);
            } else {
                logger.warn("User with id {} not found", id);
            }
            return user;
        }  catch (Exception e) {
            throw new RuntimeException("Request User wasn't found " + id, e);
        }

    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(int id) {
        Transaction transaction =  null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
          transaction = session.beginTransaction();
          User user = session.get(User.class, id);
          if(user != null){
              session.delete(user);
          }
          transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            } else {
                throw e;
            }
        }
    }
}
