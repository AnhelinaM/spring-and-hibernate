package by.anhelinam.sql.dao;

import by.anhelinam.sql.entity.Student;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public interface StudentDao {
    Set<Student> getAll() throws SQLException;

    Student getOne(long id) throws SQLException;

    Student updateOne(long id, String name, Date birthday, int grade) throws SQLException;

    Student addOne(String name, Date birthday, int grade) throws SQLException;

    void delete(long id) throws SQLException;
}
