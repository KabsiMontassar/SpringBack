package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
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
    Order order;

    String datePaiement;

    float montant;

    @Enumerated(EnumType.STRING)
    ModePaiement modePaiement;

    Long transactionId;

    @Enumerated(EnumType.STRING)
    StatusPaiement statusPaiement;
}