package o.springback.Interfaces.GestionArticle;


import o.springback.entities.GestionArticle.PanierArticle;

import java.util.List;

public interface IPanierArticleService {
    List<PanierArticle> findAll();
    PanierArticle findById(Long idPanierArticle);
    PanierArticle save(PanierArticle panierArticle);
    PanierArticle update(PanierArticle panierArticle);
    void delete(Long idPanierArticle);
}
