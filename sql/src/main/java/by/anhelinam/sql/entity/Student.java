package by.anhelinam.sql.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "student")
@Proxy(lazy=false)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthday;
    private Integer grade;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private final Set<Payment> payments = new HashSet<>();

    public Student() {
    }

    public Student(Long id, String name, LocalDate birthday, Integer grade) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.grade = grade;
    }

    public Student(String name, LocalDate birthday, Integer grade) {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
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
        return Objects.equals(id, student.id) &&
                Objects.equals(grade, student.grade) &&
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