package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    private Payment payment;

    @ManyToOne
    @JsonBackReference("article-reservations")
    private Article article;

}
