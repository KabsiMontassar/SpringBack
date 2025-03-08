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
public class Agriculteur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgriculteur;
    private String localisation;


}
