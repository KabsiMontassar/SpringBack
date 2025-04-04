package o.springback.entities.GestionCommande;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import o.springback.entities.GestionProduits.Products;
import o.springback.entities.GestionUser.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCommande;

    LocalDate dateCommande;

    @ManyToOne
    private Client client;  // Lien vers l'utilisateur

    @Enumerated(EnumType.STRING)
    private StatusCommande status;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;

    private BigDecimal montantTotal;

    // Méthode pour calculer le total
    public void calculerTotal() {
        this.montantTotal = lignes.stream()
                .map(l -> l.getProduct().getPrix().multiply(BigDecimal.valueOf(l.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addLigne(LigneCommande ligne) {
        if (lignes == null) {
            lignes = new java.util.ArrayList<>();
        }
        lignes.add(ligne);
        ligne.setCommande(this);
    }
}