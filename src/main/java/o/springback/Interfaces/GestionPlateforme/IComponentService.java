package o.springback.Interfaces.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Component;

import java.util.List;
import java.util.Map;

public interface IComponentService {
    Component save(Component component);
    Component update( Component component);
    void delete(Long id);
    Component findById(Long id);
    List<Component> findAll();
    List<Component> findByUserId(Long userId);

    Map<String,Double> getUsageRate();
}
