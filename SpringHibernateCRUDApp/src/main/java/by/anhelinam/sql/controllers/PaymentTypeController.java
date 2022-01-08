package by.anhelinam.sql.controllers;

import by.anhelinam.sql.dao.PaymentTypeDAO;
import by.anhelinam.sql.models.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/types")
public class PaymentTypeController {
    private final PaymentTypeDAO paymentTypeDAO;

    @Autowired
    public PaymentTypeController(PaymentTypeDAO paymentTypeDAO) {
        this.paymentTypeDAO = paymentTypeDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("types", paymentTypeDAO.index());
        return "types/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("paymentType", paymentTypeDAO.show(id));
        return "types/show";
    }

    @GetMapping("/new")
    public String newPaymentType(@ModelAttribute("paymentType") PaymentType paymentType) {
        return "types/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("paymentType") @Valid PaymentType paymentType,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "types/new";

        paymentTypeDAO.save(paymentType);
        return "redirect:/types";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("paymentType", paymentTypeDAO.show(id));
        return "types/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("paymentType") @Valid PaymentType paymentType, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "types/edit";

        paymentTypeDAO.update(id, paymentType);
        return "redirect:/types";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        paymentTypeDAO.delete(id);
        return "redirect:/types";
    }
}
