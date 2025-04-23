package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IReservationService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Payment;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.PaymentRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ArticleRepository articleRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + idReservation));
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getArticle() == null || reservation.getArticle().getId() == null) {
            throw new IllegalArgumentException("Article is required for a reservation.");
        }
        Article article = articleRepository.findById(reservation.getArticle().getId())
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + reservation.getArticle().getId()));
        reservation.setArticle(article);

        if (reservation.getStartDatetime() != null && reservation.getEndDatetime() != null) {
            long hours = Duration.between(reservation.getStartDatetime(), reservation.getEndDatetime()).toHours();
            if (hours <= 0) {
                throw new IllegalArgumentException("End datetime must be after start datetime.");
            }
            float totalPrice = hours * article.getPricePerHour();
            reservation.setTotalPrice(totalPrice);
        } else {
            throw new IllegalArgumentException("Start and end datetime are required for a reservation.");
        }

        if (reservation.getId() != null) {
            return reservationRepository.save(reservation);
        } else {
            return reservationRepository.save(reservation);
        }
    }
    @Override
    public Reservation update(Reservation reservation) {
        // Récupérer la réservation existante
        Reservation existing = findById(reservation.getId());

        // Mettre à jour les champs modifiables
        existing.setStartDatetime(reservation.getStartDatetime());
        existing.setEndDatetime(reservation.getEndDatetime());
        existing.setStatus(reservation.getStatus());

        // Vérifier et mettre à jour l'article (si fourni)
        if (reservation.getArticle() != null && reservation.getArticle().getId() != null) {
            Article article = articleRepository.findById(reservation.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found with id: " + reservation.getArticle().getId()));
            existing.setArticle(article);
        }

        // Vérifier et mettre à jour le paiement (si fourni)
        if (reservation.getPayment() != null && reservation.getPayment().getId() != null) {
            Payment payment = paymentRepository.findById(reservation.getPayment().getId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with id: " + reservation.getPayment().getId()));
            existing.setPayment(payment);
        }

        // Recalculer le prix total si les dates sont modifiées
        if (existing.getStartDatetime() != null && existing.getEndDatetime() != null) {
            long hours = Duration.between(existing.getStartDatetime(), existing.getEndDatetime()).toHours();
            if (hours <= 0) {
                throw new IllegalArgumentException("End datetime must be after start datetime.");
            }
            float totalPrice = hours * existing.getArticle().getPricePerHour();
            existing.setTotalPrice(totalPrice);
        }

        // Enregistrer les modifications
        return reservationRepository.save(existing);
    }

    @Override
    public void delete(Long idReservation) {
        if (!reservationRepository.existsById(idReservation)) {
            throw new RuntimeException("Reservation not found with id: " + idReservation);
        }
        reservationRepository.deleteById(idReservation);
    }

    @Override
    public List<Reservation> findByArticleId(Long articleId) {
        return reservationRepository.findByArticleId(articleId);
    }
}