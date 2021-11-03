package by.anhelinam.sql.entity;

import java.sql.Date;
import java.util.Objects;

public class Payment {
    private long id;
    private PaymentType paymentType;
    private double amount;
    private Student student;
    private Date date;

    public Payment(long id, PaymentType paymentType, double amount, Student student, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id &&
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
