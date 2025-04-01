package o.springback.entities.GestionFormation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFormation;

    private String nom;
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private String lieu;
    private boolean certification;

    @Enumerated(EnumType.STRING)
    private TypeFormation typeFormation;

    @OneToOne(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DetailsFormation detailFormation;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Participation> participations;
}