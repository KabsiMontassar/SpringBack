package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import o.springback.entities.GestionUser.Livreur;

import java.time.LocalDateTime;

// Position.java
@Entity
@Getter
@Setter
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    @ManyToOne
    private Livreur livreur;
}