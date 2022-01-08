package by.anhelinam.sql.models;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @Min(value = 1, message = "Grade should be greater than 0")
    @Max(value = 6, message = "Grade should be less than 7")
    private Long grade;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private final Set<by.anhelinam.sql.models.Payment> payments = new HashSet<>();

    public Student() {
    }

    public Student(Long id, String name, LocalDate birthday, Long grade) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.grade = grade;
    }

    public Student(String name, LocalDate birthday, Long grade) {
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
    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getPayments() {
//        String result = '';
//        for (p: payments){
//            result += p.g''
//        }
        return payments.toString();
    }

    public void setPayment(by.anhelinam.sql.models.Payment payment) {
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