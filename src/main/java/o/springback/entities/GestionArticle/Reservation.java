package o.springback.entities.GestionArticle;

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
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

}
