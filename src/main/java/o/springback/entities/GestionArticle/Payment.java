package o.springback.entities.GestionArticle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float amount;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private Long referenceId;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    private LocalDateTime paymentDate = LocalDateTime.now();
    public enum PaymentType {
        AUCTION, RESERVATION
    }
    public enum Status {
        PENDING, COMPLETED, FAILED
    }

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}
