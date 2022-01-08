package by.anhelinam.sql.dao.impl;

import by.anhelinam.sql.dao.StudentDao;
import by.anhelinam.sql.entity.Student;
import by.anhelinam.sql.exception.ConnectionPoolException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class HibernateStudentDao implements StudentDao {
    private final SessionFactory sessionFactory;

    public HibernateStudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Set<Student> getAll() throws SQLException, InterruptedException, ConnectionPoolException {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Student");
            return new HashSet<Student>(query.list());
        }
    }

    @Override
    public Student getOne(long id) throws SQLException, InterruptedException, ConnectionPoolException {
        try (Session session = sessionFactory.openSession()) {
            return session.load(Student.class, id);
        }
    }

    @Override
    public Student updateOne(long id, String name, Date birthday, int grade) throws SQLException, InterruptedException, ConnectionPoolException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Student student = new Student(id, name, birthday.toLocalDate(), grade);
            session.saveOrUpdate(student);
            tx.commit();
            return student;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Student addOne(String name, Date birthday, int grade) throws SQLException, InterruptedException, ConnectionPoolException {
        try (Session session = sessionFactory.openSession()) {
            Student student = new Student(name, birthday.toLocalDate(), grade);
            session.save(student);
            return student;
        }
    }

    @Override
    public void delete(long id) throws SQLException, InterruptedException, ConnectionPoolException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(session.load(Student.class, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
