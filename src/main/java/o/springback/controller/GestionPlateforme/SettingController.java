package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.ISettingService;
import o.springback.entities.GestionPlateforme.Settings;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/settings")
public class SettingController {

    ISettingService settingService;

    @GetMapping("/retrieve-all-settings")
    public List<Settings> getsettings() {
        return settingService.findAll();
    }
    @GetMapping("/retrieve-settings/{settings-id}")
    public Settings retrievesettings(@PathVariable("settings-id") Long settingsId) {
        return settingService.findById(settingsId);
    }
    @PostMapping("/add-settings")
    public Settings addsettings(@RequestBody Settings c) {
        return settingService.save(c);
    }
    @DeleteMapping("/remove-settings/{settings-id}")
    public void removesettings(@PathVariable("settings-id") Long settingsId) {
        settingService.delete(settingsId);
    }
    @PutMapping("/update-settings")
    public Settings updatesettings(@RequestBody Settings c) {
        return settingService.update(c);
    }
}
