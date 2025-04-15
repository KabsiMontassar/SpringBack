package o.springback.entities.GestionPlanningEmployee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTache")
    private Long idTache;
    private String titre;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private StatutTache statutTache;
    @ManyToOne
    private Employee employee;
    @OneToMany(mappedBy = "tache")
    private List<Planning> plannings;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval tfassakh l'entité elli maadch aandha parent (orphaned entity) mel db
    private List<Tache> sousTaches = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore //pour éviter les boucles
    private Tache parent;

}