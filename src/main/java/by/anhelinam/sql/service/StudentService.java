package by.anhelinam.sql.service;

import by.anhelinam.sql.entity.Student;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.exception.ValidationException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public interface StudentService {
    Set<Student> getAll() throws SQLException, InterruptedException, ConnectionPoolException;

    Student getOne(long id) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException;

    Student updateOne(long id, String name, Date birthday, int grade) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException;

    Student addOne(String name, Date birthday, int grade) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException;

    void delete(long id) throws ValidationException, SQLException, InterruptedException, ConnectionPoolException;
}
