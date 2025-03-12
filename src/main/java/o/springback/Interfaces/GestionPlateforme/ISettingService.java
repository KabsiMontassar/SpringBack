package o.springback.Interfaces.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Settings;

import java.util.List;

public interface ISettingService {
    Settings save(Settings settings);
    Settings update(Settings settings);
    void delete(Long id);
    Settings findById(Long id);
    List<Settings> findAll();
}
