package o.springback.entities.GestionProduits;

import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionCommande.Commande;
import o.springback.entities.GestionCommande.LigneCommande;
import o.springback.entities.GestionUser.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "produits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit;
    private String nom;
    private String description;
    private Long prix;
    private Long quantiteDisponible;
    private LocalDate dateAjout;
    private String imageURL;
    private String status;

    // Relation avec Stock (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "idStock")
    private Stock stock;

    // Relation avec Avis (OneToMany)
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private Set<AvisProduct> avis;

    // Relation avec Catégorie (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private CategorieProduct categorie;

    // Relation avec Commandes (ManyToMany)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<LigneCommande> ligneCommandes;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}
