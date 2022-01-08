package by.anhelinam.sql.dao;

import by.anhelinam.sql.models.Payment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PaymentDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PaymentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Payment> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Payment p", Payment.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Payment show(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Payment.class, id);
    }

    @Transactional
    public void save(Payment payment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(payment);
    }

    @Transactional
    public void update(Long id, Payment updatedPayment) {
        Session session = sessionFactory.getCurrentSession();
        Payment paymentToBeUpdated = session.get(Payment.class, id);

        paymentToBeUpdated.setAmount(updatedPayment.getAmount());
        paymentToBeUpdated.setDate(updatedPayment.getDate());
        paymentToBeUpdated.setPaymentType(updatedPayment.getPaymentType());
        paymentToBeUpdated.setStudent(updatedPayment.getStudent());
    }

    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Payment.class, id));
    }
}
