package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.PaymentArticle;

import java.util.List;

public interface IPaymentService {
    List<PaymentArticle> findAll();
    PaymentArticle findById(Long idPayment);
    PaymentArticle save(PaymentArticle payment);
    PaymentArticle update(PaymentArticle payment);
    void delete(Long idPayment);
}
