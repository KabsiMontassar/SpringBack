package o.springback.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data //combinaison de @Getter et @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends UserDTO{
    private String historiqueCommandes;
}
