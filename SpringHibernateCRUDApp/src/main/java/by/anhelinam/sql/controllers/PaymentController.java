package by.anhelinam.sql.controllers;

import by.anhelinam.sql.dao.PaymentDAO;
import by.anhelinam.sql.dao.PaymentTypeDAO;
import by.anhelinam.sql.dao.StudentDAO;
import by.anhelinam.sql.models.Payment;
import by.anhelinam.sql.models.PaymentDto;
import by.anhelinam.sql.models.PaymentType;
import by.anhelinam.sql.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentDAO paymentDAO;
    private final PaymentTypeDAO paymentTypeDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public PaymentController(PaymentDAO paymentDAO, PaymentTypeDAO paymentTypeDAO, StudentDAO studentDAO) {
        this.paymentDAO = paymentDAO;
        this.paymentTypeDAO = paymentTypeDAO;
        this.studentDAO = studentDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("payments", paymentDAO.index());
        return "payments/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("payment", paymentDAO.show(id));
        return "payments/show";
    }

    @GetMapping("/new")
    public String newPayment(@ModelAttribute("payment") PaymentDto formData, Model model) {
        List<PaymentType> types = paymentTypeDAO.index();
        model.addAttribute("types", types);
        List<Student> students = studentDAO.index();
        model.addAttribute("students", students);
        return "payments/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("payment") PaymentDto formData,
//                         @ModelAttribute("payment") @Valid Payment payment,
                         BindingResult bindingResult) {
        Payment payment = new Payment();
        payment.setStudent(studentDAO.show(formData.getStudentId()));
        payment.setDate(formData.getDate());
        payment.setPaymentType(paymentTypeDAO.show(formData.getPaymentTypeId()));
        payment.setAmount(formData.getAmount());
        if (bindingResult.hasErrors())
            return "payments/new";

        paymentDAO.save(payment);
        return "redirect:/payments";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
//        , @ModelAttribute("paymentType") Long typeId, @ModelAttribute("student") Long studentId
        List<PaymentType> types = paymentTypeDAO.index();
        model.addAttribute("types", types);
        List<Student> students = studentDAO.index();
        model.addAttribute("students", students);
        model.addAttribute("payment", PaymentDto.fromTeam(paymentDAO.show(id)));
        return "payments/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("payment") PaymentDto formData,
//            @ModelAttribute("payment") @Valid Payment payment, @ModelAttribute("paymentType") @Valid Long paymentTypeId,
//                         @ModelAttribute("student") @Valid Long studentId,
                         BindingResult bindingResult) {
//        System.out.println(payment);
        Payment payment = paymentDAO.show(id);
        payment.setStudent(studentDAO.show(formData.getStudentId()));
        payment.setDate(formData.getDate());
        payment.setPaymentType(paymentTypeDAO.show(formData.getPaymentTypeId()));
        payment.setAmount(formData.getAmount());
//        payment.setPaymentType(paymentTypeDAO.show(paymentTypeId));
//        payment.setStudent(studentDAO.show(studentId));
        System.out.println(payment);
        if (bindingResult.hasErrors())
            return "payments/edit";

        paymentDAO.update(id, payment);
        return "redirect:/payments";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        paymentDAO.delete(id);
        return "redirect:/payments";
    }
}
