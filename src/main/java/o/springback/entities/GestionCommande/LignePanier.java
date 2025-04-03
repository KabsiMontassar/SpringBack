package o.springback.entities.GestionCommande;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LignePanier {
    @Id
    Long idPaiement;

    @OneToOne
    Commande commande;

    String datePaiement;

    float montant;

    @Enumerated(EnumType.STRING)
    ModePaiement modePaiement;

    Long transactionId;

    @Enumerated(EnumType.STRING)
    StatusPaiement statusPaiement;
}
