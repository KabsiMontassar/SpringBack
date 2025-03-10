package o.springback.entities.GestionUser;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("CLIENT")
public class Client extends User {

    private String historiqueCommandes;


}
