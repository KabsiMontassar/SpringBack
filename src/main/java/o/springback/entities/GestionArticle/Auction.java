package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start price is required")
    @Positive(message = "Start price must be positive")
    private Float startPrice;

    @NotNull(message = "Current price is required")
    @Positive(message = "Current price must be positive")
    private Float currentPrice;

    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Active status is required")
    private boolean isActive = true;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonBackReference("article-auction")
    private Article article;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonManagedReference("auction-bids")
    private List<Bid> bids;

    @OneToOne
    @JsonManagedReference("auction-payment")
    private Payment payment;

}