package o.springback.entities.GestionPlateforme;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSponsor;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 ]*$", message = "Name must start with a letter and can only contain letters, numbers, and spaces")
    private String nomSponsor;

    private String logo;

    private LocalDate datepartenariat;

    @ManyToOne
    @JoinColumn(name = "plateforme_id", nullable = false)
    @JsonIgnore
    Plateforme plateformeSponsor;
}
