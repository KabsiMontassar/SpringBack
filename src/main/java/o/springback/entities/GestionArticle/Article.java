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
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Float pricePerHour;
    private boolean isAvailable;
    private LocalDateTime createdAt;
    @OneToOne
    private Auction auction;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}




