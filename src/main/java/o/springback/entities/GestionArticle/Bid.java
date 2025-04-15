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
public class Bid {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float bidAmount;
    private LocalDateTime bidTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;
}
