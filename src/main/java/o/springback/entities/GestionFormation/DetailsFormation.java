package o.springback.entities.GestionFormation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;



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
    @JsonBackReference("formation-detail")
    private Formation formation;

}
