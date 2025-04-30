package o.springback.entities.GestionPlanningEmployee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private Long idEmployee;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @Temporal(TemporalType.DATE)
    Date dateEmbauche;
    @Enumerated(EnumType.STRING)
    private TypePost typePoste;
    private Float salaire;
    @ManyToOne
    @JsonIgnore
    private Plateforme plateforme;
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Tache> taches;




}
