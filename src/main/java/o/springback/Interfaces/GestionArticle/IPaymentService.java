package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.Payment;

import java.util.List;

public interface IPaymentService {
    List<Payment> findAll();
    Payment findById(Long idPayment);
    Payment save(Payment payment);
    Payment update(Payment payment);
    void delete(Long idPayment);
}
