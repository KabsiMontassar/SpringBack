package o.springback.entities.GestionProduits;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionCommande.Order;
import o.springback.entities.GestionUser.User;

import java.math.BigDecimal;
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
    private Double prix;
    private Long quantiteDisponible;
    private LocalDate dateAjout = LocalDate.now();
    private String imageURL;
    private String status;

    // Utiliser JsonIgnoreProperties pour éviter les problèmes de boucles infinies
    @JsonIgnoreProperties("produits")
    @ManyToOne
    @JoinColumn(name = "idStock")
    private Stock stock;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private Set<AvisProduct> avis;

    @JsonIgnoreProperties("produits")
    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private CategorieProduct categorie;

    /* Relation avec Commandes (ManyToMany)
    @ManyToMany
    @JsonIgnoreProperties
    @JoinTable(
    name = "produit_commande", joinColumns = @JoinColumn(name = "idProduit"), inverseJoinColumns = @JoinColumn(name = "idCommande"))
    private Set<Order> orders;*/

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("products")
    private User user;
}
