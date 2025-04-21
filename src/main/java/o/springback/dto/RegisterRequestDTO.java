package o.springback.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.validation.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "Nom is mandatory")
    private String nom;
    @NotBlank(message = "Prenom is mandatory")
    private String prenom;
    @Email(message= "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Telephone is mandatory")
    private String telephone;
    @NotBlank(message = "Password is mandatory")
    @Size(min=6, message = "Password should be at least 6 characters")
    private String password;
    private String adresse;
    private String role;
}
