package o.springback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long idUser;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;
    private String adresse;
    private String role;
    private Date dateInscription;
}
