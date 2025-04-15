package o.springback.entities.GestionCommande;

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

    String dateLivraison;

    @OneToOne
    Order order;

    @OneToOne
    Livreur livreur;

    LocalDate dateLivraisonEffective;

    LocalDate dateLivraisonPrevue;

    @Enumerated(EnumType.STRING)
    StatusLivraison statusLivraison;
}