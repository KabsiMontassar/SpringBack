package o.springback.entities.GestionCommande;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import o.springback.entities.GestionUser.Livreur;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPanier;


    private String adresse;
    private String description;
    private double latitude;
    private double longitude;

    @OneToOne
    @JoinColumn(name = "commande_id")
    @JsonIgnore
    Order order;

    @Enumerated(EnumType.STRING)
    StatusLivraison statusLivraison;
}