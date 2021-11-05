package by.anhelinam.sql.dao;

import by.anhelinam.sql.entity.Student;
import by.anhelinam.sql.exception.ConnectionPoolException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public interface StudentDao {
    Set<Student> getAll() throws SQLException, InterruptedException, ConnectionPoolException;

    Student getOne(long id) throws SQLException, InterruptedException, ConnectionPoolException;

    Student updateOne(long id, String name, Date birthday, int grade) throws SQLException, InterruptedException, ConnectionPoolException;

    Student addOne(String name, Date birthday, int grade) throws SQLException, InterruptedException, ConnectionPoolException;

    void delete(long id) throws SQLException, InterruptedException, ConnectionPoolException;
}
