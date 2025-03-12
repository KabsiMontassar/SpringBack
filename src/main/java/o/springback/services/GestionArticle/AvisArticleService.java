package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IAvisArticleService;
import o.springback.entities.GestionArticle.AvisArticle;
import o.springback.repositories.GestionArticle.AvisArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AvisArticleService implements IAvisArticleService {
    private final AvisArticleRepository avisArticleRepository;

    @Override
    public List<AvisArticle> findAll() {
        return avisArticleRepository.findAll();
    }

    @Override
    public AvisArticle findById(Long idAvisArticle) {
        return avisArticleRepository.findById(idAvisArticle).orElse(null);
    }

    @Override
    public AvisArticle save(AvisArticle avisArticle) {
        return avisArticleRepository.save(avisArticle);
    }

    @Override
    public AvisArticle update(AvisArticle avisArticle) {
        return avisArticleRepository.save(avisArticle);
    }

    @Override
    public void delete(Long idAvisArticle) {
        avisArticleRepository.deleteById(idAvisArticle);

    }
}
