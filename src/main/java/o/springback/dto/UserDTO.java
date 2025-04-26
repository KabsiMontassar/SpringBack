package o.springback.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import o.springback.entities.GestionPlateforme.TypePack;
import o.springback.entities.GestionUser.User;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long idUser;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String nom;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String prenom;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must contain 8 digits")
    private String telephone;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String adresse;

    private Date dateInscription;

    @Size(max = 50, message = "Role cannot exceed 50 characters")
    private String role;

    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String imageUrl;

    private TypePack typePack;

    private Long plateformeId;

    // conversion
    public void updateEntityFields(User user) {
        if (this.nom != null) user.setNom(this.nom);
        if (this.prenom != null) user.setPrenom(this.prenom);
        if (this.email != null) user.setEmail(this.email);
        if (this.telephone != null) user.setTelephone(this.telephone);
        if (this.password != null) user.setPassword(this.password);
        if (this.adresse != null) user.setAdresse(this.adresse);
        if (this.role != null) user.setRole(this.role);
        if (this.typePack != null) user.setTypePack(this.typePack);
    }
}