package o.springback.entities.GestionFormation;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipation;

    private int idAgriculteur;

    @Temporal(TemporalType.DATE)
    private Date dateInscription;

    private float noteFinale;
    private boolean certificatDelivre;

    @ManyToOne
    @JoinColumn(name = "idFormation")
    private Formation formation;

    // Getters and Setters
    public int getIdParticipation() {
        return idParticipation;
    }

    public void setIdParticipation(int idParticipation) {
        this.idParticipation = idParticipation;
    }

    public int getIdAgriculteur() {
        return idAgriculteur;
    }

    public void setIdAgriculteur(int idAgriculteur) {
        this.idAgriculteur = idAgriculteur;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public float getNoteFinale() {
        return noteFinale;
    }

    public void setNoteFinale(float noteFinale) {
        this.noteFinale = noteFinale;
    }

    public boolean isCertificatDelivre() {
        return certificatDelivre;
    }

    public void setCertificatDelivre(boolean certificatDelivre) {
        this.certificatDelivre = certificatDelivre;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
