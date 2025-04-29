package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IComponentService;
import o.springback.entities.GestionPlateforme.Component;
import o.springback.repositories.GestionPlateformeRepository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComponentService implements IComponentService {
    private final ComponentRepository componentRepository;

    @Override
    public Component save(Component component) {
        return componentRepository.save(component);
    }

    @Override
    public Component update( Component component) {
        return componentRepository.save(component);
    }

    @Override
    public void delete(Long id) {
        componentRepository.deleteById(id);
    }

    @Override
    public Component findById(Long id) {
        return componentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Component> findAll() {
        return componentRepository.findAll();
    }

    @Override
    public List<Component> findByUserId(Long userId) {
        return componentRepository.findByIdUser(userId);
    }



    @Override
    public Map<String, Double> getUsageRate() {
        List<String> ComponentType = List.of(
                "headerwithicons", "centeredhero", "herowithimage", "verticallycenteredhero",
                "columnswithicons", "customcards", "headings", "headingleftwithimage",
                "headingrightwithimage", "newsletter", "plateformeabout"
        );

        List<Component> components = componentRepository.findAll();
        int total = components.size();

        Map<String, Double> usageRate = components.stream()
                .collect(Collectors.groupingBy(Component::getType, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round(((double) entry.getValue() / total) * 1000) / 10.0 // ← one decimal place
                ));

        for (String type : ComponentType) {
            usageRate.putIfAbsent(type, 0.0);
        }

        return usageRate;
    }


}
