package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.ICommentaireService;
import o.springback.entities.GestionArticle.Annonce;
import o.springback.entities.GestionArticle.Commentaire;
import o.springback.repositories.GestionArticle.AnnonceRepository;
import o.springback.repositories.GestionArticle.CommentaireRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentaireService implements ICommentaireService {
    private final CommentaireRepository commentaireRepository;
    private final AnnonceRepository annonceRepository;

    @Override
    public List<Commentaire> findAll() {
        return commentaireRepository.findAll();
    }

    @Override
    public Commentaire findById(Long idCommentaire) {
        return commentaireRepository.findById(idCommentaire).orElse(null);
    }

    @Override
    public Commentaire save(Commentaire commentaire) {
        if (commentaire.getAnnonce() != null && commentaire.getAnnonce().getIdAnnonce() != null) {
            Annonce annonce = annonceRepository.findById(commentaire.getAnnonce().getIdAnnonce()).orElse(null);
            if (annonce != null) {
                commentaire.setAnnonce(annonce);
                return commentaireRepository.save(commentaire);
            }
        }
        return commentaireRepository.save(commentaire);
    }

    @Override
    public Commentaire update(Commentaire commentaire) {
        Commentaire existingCommentaire = commentaireRepository.findById(commentaire.getIdCommentaire()).orElse(null);
        if (existingCommentaire == null) {
            return null;
        }
        if (commentaire.getAnnonce() != null && commentaire.getAnnonce().getIdAnnonce() != null) {
            Annonce annonce = annonceRepository.findById(commentaire.getAnnonce().getIdAnnonce()).orElse(null);
            if (annonce != null) {
                commentaire.setAnnonce(annonce);
            }
        }

        return commentaireRepository.save(commentaire);
    }

    @Override
    public void delete(Long idCommentaire) {
        commentaireRepository.deleteById(idCommentaire);
    }

}