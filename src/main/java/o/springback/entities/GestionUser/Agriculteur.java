package o.springback.entities.GestionUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.Plateforme;
import java.util.List;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@DiscriminatorValue("AGRICULTEUR")
public class Agriculteur extends User {

    private String localisation;



    @OneToMany(mappedBy = "agriculteur")
    private List<Voucher> vouchers;
}
