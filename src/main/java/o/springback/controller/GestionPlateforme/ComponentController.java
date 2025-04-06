package o.springback.controller.GestionPlateforme;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlateforme.IComponentService;
import o.springback.entities.GestionPlateforme.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components")
@AllArgsConstructor
public class ComponentController {
    private final IComponentService componentService;

    @PostMapping
    public Component addComponent(@RequestBody Component component) {
        return componentService.save(component);
    }

    @PutMapping
    public Component updateComponent( @RequestBody Component component) {
        return componentService.save(component);
    }

    @DeleteMapping("/{id}")
    public void deleteComponent(@PathVariable Long id) {
        componentService.delete(id);
    }

    @GetMapping("/{id}")
    public Component getComponentById(@PathVariable Long id) {
        return componentService.findById(id);
    }

    @GetMapping
    public List<Component> getAllComponents() {
        return componentService.findAll();
    }

    //fetch all components by userid
    @GetMapping("/user/{userId}")
    public List<Component> getComponentsByUserId(@PathVariable Long userId) {
        return componentService.findByUserId(userId);
    }





}
