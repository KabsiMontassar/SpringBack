package o.springback.entities.gestionUser;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Client extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLivreur;

}
