package o.springback.entities.GestionUser;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role", discriminatorType = DiscriminatorType.INTEGER)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String identifiant;
}
