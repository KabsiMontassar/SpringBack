package o.springback.controller.GestionCommande;

import o.springback.entities.GestionCommande.Position;
import o.springback.services.GestionCommande.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<Position> updatePosition(@RequestBody Position position) {
        position.setTimestamp(LocalDateTime.now());
        positionService.broadcastPosition(position);
        return ResponseEntity.ok(position);
    }
}
