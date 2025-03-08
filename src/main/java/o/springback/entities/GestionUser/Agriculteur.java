package o.springback.entities.GestionUser;

import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("1")
public class Agriculteur extends User {

    private String localisation;

    @OneToOne
    private Plateforme plateforme;
}
