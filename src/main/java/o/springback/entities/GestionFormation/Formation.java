package o.springback.entities.GestionFormation;



import jakarta.persistence.*;


import java.util.Date;

@Entity
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFormation;

    private String nom;
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private String lieu;
    private boolean certification;

    @Enumerated(EnumType.STRING)
    private TypeFormation typeFormation;

    @OneToOne(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DetailsFormation detailFormation;

    // Getters and Setters
    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public boolean isCertification() {
        return certification;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public TypeFormation getTypeFormation() {
        return typeFormation;
    }

    public void setTypeFormation(TypeFormation typeFormation) {
        this.typeFormation = typeFormation;
    }

    public DetailsFormation getDetailFormation() {
        return detailFormation;
    }

    public void setDetailFormation(DetailsFormation detailFormation) {
        this.detailFormation = detailFormation;
    }
}