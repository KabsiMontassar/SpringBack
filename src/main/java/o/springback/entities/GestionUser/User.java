package o.springback.entities.GestionUser;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {
    @Id
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;
    private String adresse;
    @Temporal(TemporalType.DATE)
    Date dateInscription;
    private String identifiant;
}
