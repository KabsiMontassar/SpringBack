package o.springback.entities.GestionCommande;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Paiement {
    @Id
    Long idPaiement;

    @OneToOne
    Commande commande;

    String datePaiement;

    float montant;

    String modePaiement;

    Long transactionId;

    StatusPaiement statusPaiement;
}
