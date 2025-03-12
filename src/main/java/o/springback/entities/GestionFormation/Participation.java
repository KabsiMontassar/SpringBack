package o.springback.entities.GestionFormation;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipation;

    private int idAgriculteur;

    @Temporal(TemporalType.DATE)
    private Date dateInscription;

    private float noteFinale;
    private boolean certificatDelivre;

    @ManyToOne
    @JoinColumn(name = "idFormation")
    private Formation formation;


}
