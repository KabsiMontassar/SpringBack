package o.springback.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgriculteurDTO extends UserDTO{
    private String localisation;
}
