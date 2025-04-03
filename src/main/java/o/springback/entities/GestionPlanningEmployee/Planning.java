package o.springback.entities.GestionPlanningEmployee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlanning")
    private Long idPlanning;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private TypePlanning typePlanning;
    @ManyToOne
    private Tache tache;
    @ManyToOne
    @JsonIgnore
    private Employee employee;

}