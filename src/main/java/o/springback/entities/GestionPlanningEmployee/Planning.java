package o.springback.entities.GestionPlanningEmployee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private TypePlanning typePlanning;
    @ManyToOne
    @JsonIgnore
    private Tache tache;
    @ManyToOne
    @JsonIgnore
    private Employee employee;

    //@Transient //pour dire que data hedhi ma nesthakech nsaveha fel bdd
    //private LocalTime startTimeView;
    //@Transient
    //private LocalTime endTimeView;

    public boolean isTimeSpecific(){
        return typePlanning == TypePlanning.REUNION || typePlanning == TypePlanning.FORMATION;
    }



}