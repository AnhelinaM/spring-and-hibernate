package by.anhelinam.sql.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

public class PaymentDto {
    private Long id;
    private Long paymentTypeId;
    @Min(value = 0, message = "Amount should be greater than 0")
    private Double amount;
    private Long studentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public static PaymentDto fromTeam(Payment team) {
        PaymentDto result = new PaymentDto();
//        ...
        result.setPaymentTypeId(team.getPaymentType().getId());
        result.setStudentId(team.getStudent().getId());
        result.setId(team.getId());
        result.setAmount(team.getAmount());
        result.setDate(team.getDate());
//        ...
        return result;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
