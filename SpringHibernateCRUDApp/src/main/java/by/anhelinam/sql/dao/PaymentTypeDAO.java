package by.anhelinam.sql.dao;

import by.anhelinam.sql.models.PaymentType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PaymentTypeDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PaymentTypeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
//    @Transactional
    public List<PaymentType> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select s from PaymentType s", PaymentType.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
//    @Transactional
    public PaymentType show(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(PaymentType.class, id);
    }

    @Transactional
    public void save(PaymentType paymentType) {
        Session session = sessionFactory.getCurrentSession();
        session.save(paymentType);
    }

    @Transactional
    public void update(Long id, PaymentType updatedPaymentType) {
        Session session = sessionFactory.getCurrentSession();
        PaymentType paymentTypeToBeUpdated = session.get(PaymentType.class, id);

        paymentTypeToBeUpdated.setName(updatedPaymentType.getName());
        paymentTypeToBeUpdated.setDescription(updatedPaymentType.getDescription());
    }

    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(PaymentType.class, id));
    }
}
