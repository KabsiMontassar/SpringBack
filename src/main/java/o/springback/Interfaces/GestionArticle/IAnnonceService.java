package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.Annonce;
import java.util.List;

public interface IAnnonceService {
    List<Annonce> findAll();
    Annonce findById(Long idAnnonce);
    Annonce save(Annonce annonce);
    Annonce update(Annonce annonce);
    void delete(Long idAnnonce);
}
