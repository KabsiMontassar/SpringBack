package o.springback.entities.GestionProduits;

import jakarta.persistence.*;
import o.springback.entities.GestionUser.User;

import java.time.LocalDateTime;

@Entity
    public class ProductInteraction {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private User user;

        @ManyToOne
        private Products product;

        private LocalDateTime interactionDate;
        private String typeInteraction; // "view", "purchase", "like"....
    }

