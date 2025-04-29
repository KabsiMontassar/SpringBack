package o.springback.entities.GestionFormation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import o.springback.entities.GestionUser.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Getter
@Setter
public class ParticipationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipationStatus;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "formation_idFormation")
    @OnDelete(action = OnDeleteAction.CASCADE) // 🔥🔥🔥 AJOUTER CA !!!
    private Formation formation;


    private int nombreAnnulations = 0;
    private boolean bloque = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBlocage;
}