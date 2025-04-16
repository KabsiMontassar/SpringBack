package o.springback.entities.GestionFormation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import o.springback.entities.GestionUser.User;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
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
    private boolean enAttente = false;

    @ManyToOne
    @JsonBackReference("formation-participations")
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    @JsonBackReference
    private User user; // Ajout de la relation avec User

}
