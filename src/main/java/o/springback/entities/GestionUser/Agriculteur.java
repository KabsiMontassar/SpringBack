package o.springback.entities.GestionUser;

import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Agriculteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgriculteur;

    private String localisation;

    @OneToOne
    private Plateforme plateforme;
}
