package o.springback.entities.GestionPlateforme;

import jakarta.persistence.*;
import lombok.*;

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

    private String type;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "plateforme_id", nullable = false)
    private Plateforme plateforme;
}
