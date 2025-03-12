package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import o.springback.entities.GestionUser.Client;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCommande;

    @ManyToOne
    Client client;

    LocalDate dateCommande;

    @OneToOne(mappedBy = "commande")
    Facture facture;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    List<LigneCommande> lignesCommande;

    @OneToOne(mappedBy = "commande")
    Livraison livraison;

    @Enumerated(EnumType.STRING)
    StatusCommande statusCommande;

    @OneToOne(mappedBy = "commande")
    Paiement paiement;


}
