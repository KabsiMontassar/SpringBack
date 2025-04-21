package o.springback.entities.GestionUser;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionFormation.Participation;
import o.springback.entities.GestionPlateforme.Component;
import o.springback.entities.GestionPlateforme.Plateforme;
import o.springback.entities.GestionPlateforme.TypePack;
import o.springback.entities.GestionProduits.Products;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    @Pattern(regexp = "^[0-9]{8}$", message = "Le numéro de téléphone doit contenir 8 chiffres")
    private String telephone;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    private String adresse;
    @Temporal(TemporalType.DATE)
    Date dateInscription;
    private String role;
    @Size(max = 255, message = "L'URL de l'image ne doit pas dépasser 255 caractères")
    private String verificationToken;

    @Enumerated(EnumType.STRING)
    private TypePack typePack = TypePack.GUEST;

    @OneToOne(fetch = FetchType.LAZY)
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
