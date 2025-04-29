package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionUser.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private Float totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        PENDING, CONFIRMED, CANCELLED
    }

    @OneToOne
    @JsonManagedReference("reservation-payment")
    private PaymentArticle payment;

    @ManyToOne
    @JsonBackReference("article-reservations")
    private Article article;

    @ManyToOne
    @JsonBackReference("user-reservations")
    private User user;

    @Transient // Indique que ce champ n'est pas persisté en base de données
    public String getArticleTitle() {
        return article != null ? article.getTitle() : null;
    }
}
