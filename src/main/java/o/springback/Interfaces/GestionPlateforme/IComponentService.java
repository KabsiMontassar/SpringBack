package o.springback.Interfaces.GestionPlateforme;

import o.springback.entities.GestionPlateforme.Component;

import java.util.List;

public interface IComponentService {
    Component save(Component component);
    Component update(Long id, Component component);
    void delete(Long id);
    Component findById(Long id);
    List<Component> findAll();
}
