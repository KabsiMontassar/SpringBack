package o.springback.entities.GestionArticle;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float startPrice;
    private Float currentPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive = true;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToOne
    private Payment payment;

}