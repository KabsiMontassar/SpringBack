package o.springback.Interfaces.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Plateforme;

import java.util.List;
import java.util.Map;

public interface IPlateformeService {
    Plateforme save(Plateforme plateforme);
    Plateforme update(Plateforme plateforme);
    void delete(Long id);

    Map<String,Integer> generateReport();
    Plateforme findById(Long id);
    List<Plateforme> findAll();


    void changePackType( String plan);

    Map<String, Integer> getMostlyBoughtPacks();
}
