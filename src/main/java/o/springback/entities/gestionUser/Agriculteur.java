package o.springback.entities.gestionUser;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("Agriculteur")
public class Agriculteur extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgriculteur;
    private String localisation;
}