package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IReservationService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Reservation;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.PaymentRepository;
import o.springback.repositories.GestionArticle.ReservationRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ArticleService articleService;

    private UserRepository userRepository;


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

/*    @Override
    public Reservation findById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + idReservation));
    }*/

    @Override
    public Reservation save(Reservation reservation) {
        User user = getCurrentUser();
        if(reservation.getUser() == null || reservation.getUser().getIdUser() == null) {
            throw new IllegalArgumentException("User is required for a reservation.");
        }
        reservation.setUser(user);
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
    public Reservation update(Long articleId, Long reservationId, Reservation updatedReservation) {
        Article article = articleService.findById(articleId);
        if (article == null) {
            throw new RuntimeException("Article not found with ID: " + articleId);
        }

        // Vérifier si la réservation existe
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + reservationId));

        // Vérifier si la réservation appartient bien à l'article donné
        if (!existingReservation.getArticle().getId().equals(articleId)) {
            throw new RuntimeException("Reservation " + reservationId + " does not belong to article with ID " + articleId);
        }

        // Mettre à jour les champs modifiables
        existingReservation.setStartDatetime(updatedReservation.getStartDatetime());
        existingReservation.setEndDatetime(updatedReservation.getEndDatetime());
        existingReservation.setStatus(updatedReservation.getStatus());

        // Validation des dates et calcul du prix
        if (existingReservation.getStartDatetime() != null && existingReservation.getEndDatetime() != null) {
            long hours = Duration.between(existingReservation.getStartDatetime(), existingReservation.getEndDatetime()).toHours();
            if (hours <= 0) {
                throw new IllegalArgumentException("End datetime must be after start datetime.");
            }
            float totalPrice = hours * article.getPricePerHour();
            existingReservation.setTotalPrice(totalPrice);
        }

        return reservationRepository.save(existingReservation);
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
    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findByIdWithArticle(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }
    @Override
    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserIdUser(userId);
    }


}