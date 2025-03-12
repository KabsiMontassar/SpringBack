package o.springback.Interfaces.GestionArticle;


import o.springback.entities.GestionArticle.AvisArticle;

import java.util.List;

public interface IAvisArticleService {
    List<AvisArticle> findAll();
    AvisArticle findById(Long idAvisArticle);
    AvisArticle save(AvisArticle avisArticle);
    AvisArticle update(AvisArticle avisArticle);
    void delete(Long idAvisArticle);
}
