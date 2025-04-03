package o.springback.entities.GestionProduits;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionCommande.Commande;
import o.springback.entities.GestionUser.User;

import java.time.LocalDate;
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
    private LocalDate dateAjout = LocalDate.now();
    private String imageURL;
    private String status;


    @ManyToOne
    @JoinColumn(name = "idStock")
    private Stock stock;


    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private Set<AvisProduct> avis;


    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private CategorieProduct categorie;


    @ManyToMany
    @JoinTable(
    name = "produit_commande", joinColumns = @JoinColumn(name = "idProduit"), inverseJoinColumns = @JoinColumn(name = "idCommande"))
    private Set<Commande> commandes;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("products")
    private User user;
}
