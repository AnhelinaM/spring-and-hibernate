package by.anhelinam.sql.service.impl;

import by.anhelinam.sql.dao.StudentDao;
import by.anhelinam.sql.entity.Student;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.exception.ValidationException;
import by.anhelinam.sql.service.StudentService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Set<Student> getAll() throws SQLException, InterruptedException, ConnectionPoolException {
        return studentDao.getAll();
    }

    @Override
    public Student getOne(long id) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException {
        if (id <= 0) {
            throw new ValidationException();
        }
        return studentDao.getOne(id);
    }

    @Override
    public Student updateOne(long id, String name, Date birthday, int grade) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException {
        if (id <= 0) {
            throw new ValidationException();
        }
        return studentDao.updateOne(id, name, birthday, grade);
    }

    @Override
    public Student addOne(String name, Date birthday, int grade) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException {
        if (name == null || birthday == null) {
            throw new ValidationException();
        }
        return studentDao.addOne(name, birthday, grade);
    }

    @Override
    public void delete(long id) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException {
        if (id <= 0) {
            throw new ValidationException();
        }
        studentDao.delete(id);
    }
}
