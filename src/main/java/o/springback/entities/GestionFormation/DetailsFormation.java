package o.springback.entities.GestionFormation;

import jakarta.persistence.Entity;
import jakarta.persistence.*;



@Entity
public class DetailsFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetaille;

    private String objectif;
    private String contenu;
    private int duree;
    private String materielRequis;

    @OneToOne
    @JoinColumn(name = "idFormation")
    private Formation formation;

    // Getters and Setters
    public int getIdDetaille() {
        return idDetaille;
    }

    public void setIdDetaille(int idDetaille) {
        this.idDetaille = idDetaille;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getMaterielRequis() {
        return materielRequis;
    }

    public void setMaterielRequis(String materielRequis) {
        this.materielRequis = materielRequis;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
