package o.springback.entities.GestionPlateforme;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSettings;


    private String TypeComposant ;

    private String PositionnementComposant;

    @ManyToOne
    Plateforme plateformeSetting;


}