package o.springback.entities.GestionFormation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date dateFin;

    private String lieu;
    private boolean certification;
    @Column(name = "photoPath", length = 1000, nullable = true)
    private String photoPath;

    private float noteMinPourCertificat;

    private int Capacity;



    @Enumerated(EnumType.STRING)
    private TypeFormation typeFormation;

    @OneToOne(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("formation-detail")
    private DetailsFormation detailFormation;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("formation-participations")
    private Set<Participation> participations;
}