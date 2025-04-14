package o.springback.entities.GestionPlateforme;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import o.springback.entities.GestionUser.User;

import javax.validation.constraints.*;

@Entity
@Table(name = "component")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Name can only contain letters, numbers, and spaces")
    private String name;

    @NotBlank(message = "Type cannot be blank")
    @Size(min = 1, max = 50, message = "Type must be between 1 and 50 characters")
    private String type;

    @NotNull(message = "Content cannot be null")
    @Size(max = 5000, message = "Content must be less than 5000 characters")
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;
}
