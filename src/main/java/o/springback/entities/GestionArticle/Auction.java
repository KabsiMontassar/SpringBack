package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
   // @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime startTime=LocalDateTime.now();

    @NotNull(message = "End time is required")
  //  @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Active status is required")
    private boolean isActive = true;

    @OneToOne(mappedBy = "auction")
    @JsonBackReference("article-auction")
    private Article article;


    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("auction-bids")
    private List<Bid> bids;

    @OneToOne
    @JsonManagedReference("auction-payment")
    private PaymentArticle payment;

    @Transient
    public String getArticleTitle() {
        return article != null ? article.getTitle() : null;
    }
}