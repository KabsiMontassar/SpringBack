package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IPanierArticleService;
import o.springback.entities.GestionArticle.PanierArticle;
import o.springback.repositories.GestionArticle.CommentaireRepository;
import o.springback.repositories.GestionArticle.PanierArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PanierArticleService implements IPanierArticleService {
    private final PanierArticleRepository panierArticleRepository;

    @Override
    public List<PanierArticle> findAll() {
        return panierArticleRepository.findAll();
    }

    @Override
    public PanierArticle findById(Long idPanierArticle) {
        return panierArticleRepository.findById(idPanierArticle).orElse(null);
    }

    @Override
    public PanierArticle save(PanierArticle panierArticle) {
        return panierArticleRepository.save(panierArticle);
    }

    @Override
    public PanierArticle update(PanierArticle panierArticle) {
        return panierArticleRepository.save(panierArticle);
    }

    @Override
    public void delete(Long idPanierArticle) {
        panierArticleRepository.deleteById(idPanierArticle);

    }
}
