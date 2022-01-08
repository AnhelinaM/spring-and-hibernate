package by.anhelinam.sql.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private PaymentType paymentType;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private LocalDate date;

    public Payment(long id, PaymentType paymentType, double amount, Student student, LocalDate date) {
        this.id = id;
        this.paymentType = paymentType;
        this.amount = amount;
        this.student = student;
        this.date = date;
    }

    public Payment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Double.compare(payment.amount, amount) == 0 &&
                Objects.equals(paymentType, payment.paymentType) &&
                Objects.equals(student, payment.student) &&
                Objects.equals(date, payment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentType, amount, student, date);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentType=" + paymentType +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
