package o.springback.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CommandeRequest {
    private Long clientId;
    private Map<Long, Integer> produitsQuantites; // idProduit -> quantité

    // Getters/Setters
}