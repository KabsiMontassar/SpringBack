package o.springback.entities.GestionUser;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionPlateforme.Component;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionPlateforme.TypePack;
import o.springback.entities.GestionProduits.Products;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {
    @Id
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;
    private String adresse;
    @Temporal(TemporalType.DATE)
    Date dateInscription;
    private String role;

    @Enumerated(EnumType.STRING)
    private TypePack typePack = TypePack.GUEST;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "plateforme_id")
    private Plateforme plateforme;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Component> components;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Products> produits;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Participation> participations;
}
