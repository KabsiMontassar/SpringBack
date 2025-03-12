package o.springback.entities.GestionPlateforme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import o.springback.entities.GestionUser.Agriculteur;
import o.springback.entities.GestionUser.User;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Plateforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlateforme;

    private TypePack typePack;

    private String Couleur;

    private String nomPlateforme;

    private LocalDate dateCreation;

    private LocalDate Valabilite;

    private String description;

    private String theme;

    private String logo;


    @OneToOne(mappedBy="plateforme")
    private User agriculteur;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="plateformeSetting")
    private Set<Settings> plateformeSettings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="plateformeSponsor")
    private Set<Sponsor> plateformeSponsors;




    public boolean isExpired() {
        return LocalDate.now().isAfter(Valabilite);
    }

}