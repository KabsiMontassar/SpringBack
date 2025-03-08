package o.springback.entities.gestionUser;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table
public class Agriculteur extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAgriculteur ;
}
