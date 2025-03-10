package o.springback.entities.GestionUser;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "client")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("CLIENT")
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient")
    private Long idClient;
    private String historiqueCommandes;


}
