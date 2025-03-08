package o.springback.entities.GestionUser;

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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String identifiant;
}