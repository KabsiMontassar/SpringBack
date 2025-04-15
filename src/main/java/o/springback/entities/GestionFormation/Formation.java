package o.springback.entities.GestionFormation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFormation;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-']+$", message = "Le nom contient des caractères invalides")
    private String nom;
    private String description;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future
    private Date dateFin;

    private String lieu;
    private boolean certification;
    @Column(name = "photoPath", length = 1000, nullable = true)
    private String photoPath;
    @Min(10)
    private float noteMinPourCertificat;
    @Min(value = 3)
    @Max(value = 20)
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