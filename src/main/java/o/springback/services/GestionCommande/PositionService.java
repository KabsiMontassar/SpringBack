package o.springback.services.GestionCommande;

import o.springback.entities.GestionCommande.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void broadcastPosition(Position position) {
        messagingTemplate.convertAndSend("/topic/positions", position);
    }
}