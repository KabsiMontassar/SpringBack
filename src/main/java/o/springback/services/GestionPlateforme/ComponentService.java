package o.springback.services.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IComponentService;
import o.springback.entities.GestionPlateforme.Component;
import o.springback.repositories.GestionPlateforme.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComponentService implements IComponentService {
    private final ComponentRepository componentRepository;

    @Override
    public Component save(Component component) {
        return componentRepository.save(component);
    }

    @Override
    public Component update(Long id, Component component) {
        component.setId(id);
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
}
