package o.springback.dto.GestionCommande;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String token;
    private int amount; // en centimes
    private String currency;
    private String description;
    private Long orderId;

}
