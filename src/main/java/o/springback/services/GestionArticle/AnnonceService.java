package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IAnnonceService;
import o.springback.entities.GestionArticle.Annonce;
import o.springback.repositories.GestionArticle.AnnonceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnnonceService implements IAnnonceService {
    private final AnnonceRepository annonceRepository;

    @Override
    public List<Annonce> findAll() {
        return annonceRepository.findAll();
    }

    @Override
    public Annonce findById(Long idAnnonce) {
        return annonceRepository.findById(idAnnonce).orElse(null);
    }

    @Override
    public Annonce save(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @Override
    public Annonce update(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @Override
    public void delete(Long idAnnonce) {
        annonceRepository.deleteById(idAnnonce);
    }
}
