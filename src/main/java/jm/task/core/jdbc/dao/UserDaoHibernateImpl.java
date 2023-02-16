package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory factory = getSessionFactory();
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = factory.getCurrentSession())
        {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE Users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(80), lastName VARCHAR(80), age BIGINT)").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.getCurrentSession())
        {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE Users").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
