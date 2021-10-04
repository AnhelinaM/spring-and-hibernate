package by.anhelinam.sql.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Student {
    private long id;
    private String name;
    private Date birthday;
    private int grade;
    private Set<Payment> payments = new HashSet<>();

    public Student() {
    }

    public Student(long id, String name, Date birthday, int grade) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayment(Payment payment) {
        this.payments.add(payment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                grade == student.grade &&
                Objects.equals(name, student.name) &&
                Objects.equals(birthday, student.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, grade);
    }

    @Override
    public String toString() {
        return "\nStudent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", grade=" + grade +
                ", payments=" + payments +
                '}';
    }
}