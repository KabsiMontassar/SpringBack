package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idFacture;

    @OneToOne
    Commande commande;

    LocalDate dateFacture;

    BigDecimal montantFacture;

    float tva;


}