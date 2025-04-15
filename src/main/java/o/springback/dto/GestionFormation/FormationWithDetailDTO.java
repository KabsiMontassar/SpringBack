package o.springback.dto.GestionFormation;

import lombok.Getter;
import lombok.Setter;
import o.springback.entities.GestionFormation.DetailsFormation;
import o.springback.entities.GestionFormation.Formation;
import o.springback.entities.GestionFormation.TypeFormation;

import java.util.Date;

@Getter
@Setter
public class FormationWithDetailDTO {
    private int idFormation;
    private String nom;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String lieu;
    private boolean certification;
    private float noteMinPourCertificat;
    private int capacity;
    private String photoPath;
    private TypeFormation typeFormation;
    private DetailsFormationDTO detailFormation; // Remplacé l'entité par DTO

    public FormationWithDetailDTO(Formation formation) {
        this.idFormation = formation.getIdFormation();
        this.nom = formation.getNom();
        this.description = formation.getDescription();
        this.dateDebut = formation.getDateDebut();
        this.dateFin = formation.getDateFin();
        this.lieu = formation.getLieu();
        this.certification = formation.isCertification();
        this.noteMinPourCertificat = formation.getNoteMinPourCertificat();
        this.capacity = formation.getCapacity();
        this.photoPath = formation.getPhotoPath();
        this.typeFormation = formation.getTypeFormation();

        if (formation.getDetailFormation() != null) {
            this.detailFormation = new DetailsFormationDTO(
                    formation.getDetailFormation().getIdDetaille(),
                    formation.getDetailFormation().getObjectif(),
                    formation.getDetailFormation().getContenu(),
                    formation.getDetailFormation().getDuree(),
                    formation.getDetailFormation().getMaterielRequis(),
                    formation.getIdFormation()
            );
        }
    }
}