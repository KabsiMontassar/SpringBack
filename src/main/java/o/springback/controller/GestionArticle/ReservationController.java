package o.springback.controller.GestionArticle;

import lombok.RequiredArgsConstructor;
import o.springback.Interfaces.GestionArticle.IReservationService;
import o.springback.entities.GestionArticle.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    // 🔹 Get all reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    // 🔹 Get reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    // 🔹 Create new reservation
    @PostMapping("/articles/{articleId}")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation saved = reservationService.save(reservation);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 🔹 Update existing reservation
/*    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setId(id); // Ensure ID is set
        Reservation updated = reservationService.update(reservation);
        return ResponseEntity.ok(updated);
    }*/
    @PutMapping("/articles/{articleId}/reservations/{reservationId}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long articleId,
            @PathVariable Long reservationId,
            @RequestBody Reservation updatedReservation) {

        Reservation updated = reservationService.update(articleId, reservationId, updatedReservation);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/by-article/{articleId}")
    public ResponseEntity<List<Reservation>> getReservationsByArticle(@PathVariable Long articleId) {
        List<Reservation> reservations = reservationService.findByArticleId(articleId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne 204 si aucune réservation n'est trouvée
        }
        return ResponseEntity.ok(reservations);
    }
}

