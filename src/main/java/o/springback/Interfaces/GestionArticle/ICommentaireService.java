package o.springback.Interfaces.GestionArticle;


import o.springback.entities.GestionArticle.Commentaire;

import java.util.List;

public interface ICommentaireService {
    List<Commentaire> findAll();
    Commentaire findById(Long idCommentaire);
    Commentaire save(Commentaire commentaire);
    Commentaire update(Commentaire commentaire);
    void delete(Long idCommentaire);
}
