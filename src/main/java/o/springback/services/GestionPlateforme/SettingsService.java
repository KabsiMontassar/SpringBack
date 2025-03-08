package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.ISettingService;
import o.springback.entities.GestionPlateforme.Settings;
import o.springback.repositories.GestionPlateformeRepository.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class SettingsService implements ISettingService {

    SettingsRepository settingsRepository;

    public void delete(Long id) {
        settingsRepository.deleteById(id);

    }

    public List<Settings> findAll() {
        return settingsRepository.findAll();
    }

    public Settings findById(Long id) {
        return  settingsRepository.findById(id).orElse(null);
    }

    public Settings save(Settings settings) {
        return  settingsRepository.save(settings);
    }

    public Settings update(Settings settings) {
        return  settingsRepository.save(settings);
    }


}
