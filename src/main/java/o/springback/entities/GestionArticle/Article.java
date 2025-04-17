package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Price per hour is required")
    @Positive(message = "Price per hour must be positive")
    private Float pricePerHour;

    @NotNull(message = "Availability status is required")
    private boolean isAvailable;

    @NotNull(message = "Creation date is required")
    private LocalDateTime createdAt;


    @OneToOne
    @JsonManagedReference("article-auction")
    private Auction auction;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonManagedReference("article-reservations")
    private List<Reservation> reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return isAvailable == article.isAvailable && Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(imageUrl, article.imageUrl) && Objects.equals(pricePerHour, article.pricePerHour) && Objects.equals(createdAt, article.createdAt) && Objects.equals(auction, article.auction) && Objects.equals(reservations, article.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imageUrl, pricePerHour, isAvailable, createdAt, auction, reservations);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", isAvailable=" + isAvailable +
                ", createdAt=" + createdAt +
                ", auction=" + auction +
                ", reservations=" + reservations +
                '}';
    }
}




