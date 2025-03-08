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
@DiscriminatorValue("Client")
public class Client extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String address;
}