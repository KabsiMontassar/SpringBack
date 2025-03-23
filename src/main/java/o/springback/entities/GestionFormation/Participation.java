package o.springback.entities.GestionFormation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import o.springback.entities.GestionUser.User;

import java.util.Date;

@Entity
@Getter
@Setter
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipation;

    @Temporal(TemporalType.DATE)
    private Date dateInscription;

    private float noteFinale;
    private boolean certificatDelivre;

    @ManyToOne
    @JoinColumn(name = "idFormation", nullable = false)
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user; // Ajout de la relation avec User
}