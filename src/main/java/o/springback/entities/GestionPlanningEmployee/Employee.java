package o.springback.entities.GestionPlanningEmployee;
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
    private Plateforme plateforme;
    @OneToMany(mappedBy = "employee")
    private List<Tache> taches;


    @Entity
    @AllArgsConstructor
    @Setter
    @Getter
    @NoArgsConstructor
    @ToString
    public static class Tache {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idTache")
        private Long idTache;
        private String titre;
        private String description;
        @Temporal(TemporalType.DATE)
        private Date dateDebut;
        @Temporal(TemporalType.DATE)
        private Date dateFin;
        @Enumerated(EnumType.STRING)
        private StatutTache statutTache;
        @ManyToOne
        private Employee employee;
        @OneToMany(mappedBy = "tache")
        private List<Planning> plannings;

    }
}