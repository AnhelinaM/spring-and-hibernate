package by.anhelinam.sql.dao.impl;

import by.anhelinam.sql.dao.StudentDao;
import by.anhelinam.sql.entity.Payment;
import by.anhelinam.sql.entity.PaymentType;
import by.anhelinam.sql.entity.Student;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.pool.ConnectionPool;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class StudentDaoImpl implements StudentDao {
    private final ConnectionPool connectionPool;

    public StudentDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Set<Student> getAll() throws SQLException, InterruptedException, ConnectionPoolException {
        Set<Student> studentSet = new HashSet<>();
        String queryString = "select s.id as s_id, p.id as p_id, p.amount, p.date, pt.id as pt_id, pt.name as pt_name, " +
                "s.name as s_name, s.birthday, grade " +
                "from student s " +
                "left join payment p " +
                "on p.student_id = s.id " +
                "left join payment_type pt " +
                "on p.type_id = pt.id order by s.id";
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                long nextId = resultSet.getLong("s_id");
                Student student = studentSet.stream().filter(stud -> stud.getId() == nextId).findFirst().orElse(new Student());
                Payment payment = new Payment();
                PaymentType paymentType = new PaymentType();
                payment.setId(resultSet.getLong("p_id"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setDate(resultSet.getDate("date"));
                paymentType.setId(resultSet.getLong("pt_id"));
                paymentType.setName(resultSet.getString("pt_name"));
                payment.setStudent(student);
                payment.setPaymentType(paymentType);
                student.setPayment(payment);
                if (student.getId() != nextId) {
                    student.setId(nextId);
                    student.setName(resultSet.getString("s_name"));
                    student.setBirthday(resultSet.getDate("birthday"));
                    student.setGrade(resultSet.getInt("grade"));
                    studentSet.add(student);
                }
            }
            return studentSet;
        }
    }

    @Override
    public Student getOne(long id) throws SQLException, InterruptedException, ConnectionPoolException {
        String queryString = "select p.id as p_id, p.amount, p.date, pt.id as pt_id, pt.name as pt_name, " +
                "s.name as s_name, s.birthday, grade from student s left join payment p on p.student_id = s.id " +
                "left join payment_type pt on p.type_id = pt.id where s.id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setLong(1, id); // номер вопросика начиная с 1
            ResultSet resultSet = preparedStatement.executeQuery();
            // заполняем пользователей по аналогии
            Student student = new Student();
            boolean added = false;
            while (resultSet.next()) {
                if (!added) {
                    student.setId(id);
                    student.setName(resultSet.getString("s_name"));
                    student.setBirthday(resultSet.getDate("birthday"));
                    student.setGrade(resultSet.getInt("grade"));
                    added = true;
                }
                Payment payment = new Payment();
                PaymentType paymentType = new PaymentType();
                payment.setId(resultSet.getLong("p_id"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setDate(resultSet.getDate("date"));
                paymentType.setId(resultSet.getLong("pt_id"));
                paymentType.setName(resultSet.getString("pt_name"));
                payment.setStudent(student);
                payment.setPaymentType(paymentType);
                student.setPayment(payment);
            }
            return student;
        }
    }

    @Override
    public Student updateOne(long id, String name, Date birthday, int grade) throws SQLException, InterruptedException, ConnectionPoolException {
        String queryString = "update student " +
                "set name = ?, birthday = ?, grade = ? where id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, name); // номер вопросика начиная с 1
            preparedStatement.setDate(2, birthday);
            preparedStatement.setInt(3, grade);
            preparedStatement.setLong(4, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        }
        return getOne(id);
    }

    @Override
    public Student addOne(String name, Date birthday, int grade) throws SQLException, InterruptedException, ConnectionPoolException {
        String queryString = "insert into student(name, birthday, grade) " +
                "values (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name); // номер вопросика начиная с 1
            preparedStatement.setDate(2, birthday);
            preparedStatement.setInt(3, grade);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Adding student failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getOne(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Adding student failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void delete(long id) throws SQLException, InterruptedException, ConnectionPoolException {
        String queryString = "delete from student s where s.id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting student failed, no rows affected.");
            }
        }
    }
}
