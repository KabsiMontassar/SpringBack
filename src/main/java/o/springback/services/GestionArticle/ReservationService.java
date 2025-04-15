package o.springback.services.GestionArticle;

import o.springback.Interfaces.GestionArticle.IReservationService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Payment;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.PaymentRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + idReservation));
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getArticle() != null) {
            Article article = articleRepository.findById(reservation.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found with id " + reservation.getArticle().getId()));
            reservation.setArticle(article);
        }

        if (reservation.getPayment() != null) {
            Payment payment = paymentRepository.findById(reservation.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with id " + reservation.getPayment().getId()));
            reservation.setPayment(payment);
        }

        if (reservation.getArticle() != null && reservation.getStartDatetime() != null && reservation.getEndDatetime() != null) {
            long hours = java.time.Duration.between(reservation.getStartDatetime(), reservation.getEndDatetime()).toHours();
            float price = hours * reservation.getArticle().getPricePerHour();
            reservation.setTotalPrice(price);
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        Reservation existing = reservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + reservation.getId()));

        existing.setStartDatetime(reservation.getStartDatetime());
        existing.setEndDatetime(reservation.getEndDatetime());
        existing.setStatus(reservation.getStatus());

        if (reservation.getPayment() != null) {
            Payment payment = paymentRepository.findById(reservation.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with id " + reservation.getPayment().getId()));
            existing.setPayment(payment);
        }

        if (reservation.getArticle() != null) {
            Article article = articleRepository.findById(reservation.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found with id " + reservation.getArticle().getId()));
            existing.setArticle(article);
        }

        if (existing.getArticle() != null && existing.getStartDatetime() != null && existing.getEndDatetime() != null) {
            long hours = java.time.Duration.between(existing.getStartDatetime(), existing.getEndDatetime()).toHours();
            float price = hours * existing.getArticle().getPricePerHour();
            existing.setTotalPrice(price);
        }

        return reservationRepository.save(existing);
    }

    @Override
    public void delete(Long idReservation) {
        if (!reservationRepository.existsById(idReservation)) {
            throw new RuntimeException("Reservation not found with id " + idReservation);
        }
        reservationRepository.deleteById(idReservation);
    }
}

