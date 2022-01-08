package by.anhelinam.sql.dao;

import by.anhelinam.sql.models.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StudentDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public StudentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Student> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select s from Student s", Student.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Student show(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Student.class, id);
    }

    @Transactional
    public void save(Student student) {
        Session session = sessionFactory.getCurrentSession();
        session.save(student);
    }

    @Transactional
    public void update(Long id, Student updatedStudent) {
        Session session = sessionFactory.getCurrentSession();
        Student studentToBeUpdated = session.get(Student.class, id);

        studentToBeUpdated.setName(updatedStudent.getName());
        studentToBeUpdated.setBirthday(updatedStudent.getBirthday());
        studentToBeUpdated.setGrade(updatedStudent.getGrade());
    }

    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Student.class, id));
    }
}
