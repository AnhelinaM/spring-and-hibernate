package by.anhelinam.sql.service;

import by.anhelinam.sql.entity.Student;
import by.anhelinam.sql.exception.ValidationException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public interface StudentService {
    Set<Student> getAll() throws SQLException;

    Student getOne(long id) throws ValidationException, SQLException;

    Student updateOne(long id, String name, Date birthday, int grade) throws ValidationException, SQLException;

    Student addOne(String name, Date birthday, int grade) throws ValidationException, SQLException;

    void delete(long id) throws ValidationException, SQLException;
}
