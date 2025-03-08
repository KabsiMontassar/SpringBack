package o.springback.entities.gestionUser;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User  implements Serializable   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String identifiant;


}