package o.springback.entities.GestionArticle;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Float amount;

    @NotNull(message = "Payment type cannot be null")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull(message = "Reference ID cannot be null")
    @PositiveOrZero(message = "Reference ID must be zero or positive")
    private Long referenceId;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @NotNull(message = "Payment date cannot be null")
    private LocalDateTime paymentDate = LocalDateTime.now();
    public enum PaymentType {
        AUCTION, RESERVATION
    }
    public enum Status {
        PENDING, COMPLETED, FAILED
    }

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonBackReference("auction-payment")
    private Auction auction;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonBackReference("reservation-payment")
    private Reservation reservation;

}
