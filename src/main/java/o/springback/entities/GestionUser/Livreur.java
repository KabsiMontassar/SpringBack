package o.springback.entities.GestionUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livreur")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("LIVREUR")

public class Livreur extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLivreur")
    private Long idLivreur;
    private String véhicule;
    private String zoneLivraison;
    @Enumerated(EnumType.STRING)
    private statutLivreur statutLivreur;
}
