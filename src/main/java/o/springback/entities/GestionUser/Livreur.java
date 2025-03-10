package o.springback.entities.GestionUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("LIVREUR")

public class Livreur extends User {

    private String véhicule;
    private String zoneLivraison;
    @Enumerated(EnumType.STRING)
    private statutLivreur statutLivreur;
}
