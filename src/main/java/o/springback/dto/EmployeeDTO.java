package o.springback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long idEmployee;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String typePost;
    private Float salaire;
}
