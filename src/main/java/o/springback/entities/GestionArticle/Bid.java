package o.springback.entities.GestionArticle;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Bid {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Bid amount is required")
    @Positive(message = "Bid amount must be positive")
    private Float bidAmount;

    @NotNull(message = "Bid time is required")
    @PastOrPresent(message = "Bid time cannot be in the future")
    private LocalDateTime bidTime = LocalDateTime.now();

    @ManyToOne
    @JsonBackReference("auction-bids")
    private Auction auction;
}
