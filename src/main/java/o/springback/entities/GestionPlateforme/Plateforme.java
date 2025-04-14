package o.springback.entities.GestionPlateforme;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import o.springback.entities.GestionPlanningEmployee.Employee;
import o.springback.entities.GestionUser.Agriculteur;
import o.springback.entities.GestionUser.User;
import org.springframework.cglib.core.Local;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Plateforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlateforme;


    private String couleur;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 1 and 100 characters")
    @NotNull(message = "Name cannot be null")
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 ]*$", message = "Name must start with a letter and can only contain letters, numbers, and spaces")
    private String nomPlateforme;

    private LocalDate dateCreation;

    private LocalDate valabilite;

    @NotBlank(message = "description cannot be blank")
    @Size(min = 5, max = 100, message = "description must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 ]*$", message = "description must start with a letter and can only contain letters, numbers, and spaces")
    private String description;

    private String logo;
    @Column(columnDefinition = "TEXT")
    private String content ;

    @OneToOne(mappedBy = "plateforme")
    private User agriculteur;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="plateformeSponsor")
    private Set<Sponsor> plateformeSponsors;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="plateforme")
    private Set<Employee> employees;



}