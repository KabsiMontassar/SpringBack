package o.springback.entities.GestionFormation;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter

public class DetailsFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetaille;

    private String objectif;
    private String contenu;
    private int duree;
    private String materielRequis;

    @OneToOne
    @JoinColumn(name = "idFormation")
    private Formation formation;


}
