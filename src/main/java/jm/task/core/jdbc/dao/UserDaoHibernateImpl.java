package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String TABLE_NAME = "users";
    private Transaction transaction = null;
    private static final SessionFactory sessionFactory = UtilHibernate.getSessionFactory();


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE " + TABLE_NAME +
                            "(id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL , " +
                            "name VARCHAR(40)," +
                            "lastName VARCHAR(40), " +
                            "age TINYINT CHECK ( age >= 0 ))")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Создана таблица");
        } catch (Exception e) {
            System.err.println("Ошибка создания таблицы");
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS " + TABLE_NAME)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.err.printf("Ошибка удаления таблици %s \n", TABLE_NAME);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
            System.out.printf("Добавлен user - %s %s %s \n", name, lastName, age);
        } catch (Exception e) {
            System.err.printf("Ошибка добавлеия user - %s \n", name);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            System.out.printf("User с id %d удалён \n", id);
        } catch (Exception e) {
            System.err.println("Ошибка удаления user по id = " + id);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка getAllUsers");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DELETE FROM users" ).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            System.err.println("Ошибка отчистки таблицы" + TABLE_NAME);
        }

    }
}
