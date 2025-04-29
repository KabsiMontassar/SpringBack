package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IComponentService;
import o.springback.entities.GestionPlateforme.Component;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionPlateformeRepository.ComponentRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComponentService implements IComponentService {
    private final ComponentRepository componentRepository;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Component save(Component component) {
        User user = getCurrentUser();
        component.setUser(user);
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
