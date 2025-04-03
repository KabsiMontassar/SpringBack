package o.springback.entities.GestionPlateforme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import o.springback.entities.GestionPlanningEmployee.Employee;
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

    private String couleur;

    private String nomPlateforme;

    private LocalDate dateCreation;

    private LocalDate valabilite;

    private String description;

    private String updateTheme;

    private String logo;
    private String content ;

    @OneToOne(mappedBy="plateforme")
    private User agriculteur;



    @OneToMany(cascade = CascadeType.ALL, mappedBy="plateformeSponsor")
    private Set<Sponsor> plateformeSponsors;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="plateforme")
    private Set<Employee> employees;




    public boolean isExpired() {
        return LocalDate.now().isAfter(valabilite);
    }

}