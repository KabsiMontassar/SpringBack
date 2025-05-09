package o.springback.entities.GestionArticle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import o.springback.entities.GestionUser.User;

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
    @Size(min = 10, max = 5000, message = "Description must be between 10 and 500 characters")
    private String description;

    @PositiveOrZero(message = "Prix must be positive or zero")
    private Float prix;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @PositiveOrZero(message = "Price per hour must be positive")
    private Float pricePerHour;

    @NotNull(message = "Availability status is required")
    private boolean isAvailable;

    @NotNull(message = "Article type cannot be null")
    @Enumerated(EnumType.STRING)
    private PaymentArticle.PaymentType typeArticle;


    @NotNull(message = "Creation date is required")
    private LocalDateTime createdAt;

    @Transient
    private CategoryAU category;
    public enum CategoryAU {
        commun, advanced, vip
    }
    @OneToOne
    @JsonManagedReference("article-auction")
    private Auction auction;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("article-reservations")
    private List<Reservation> reservations;

    @ManyToOne
    @JsonBackReference("user-articles")
    private User user;


}
