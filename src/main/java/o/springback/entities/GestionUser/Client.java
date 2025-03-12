package o.springback.entities.GestionUser;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionCommande.Commande;

import java.util.List;


@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("CLIENT")
public class Client extends User {

    private String historiqueCommandes;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    List<Commande> commandes;


}
