package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.Reservation;

import java.util.List;

public interface IReservationService {
    List<Reservation> findAll();
    Reservation findById(Long idReservation);
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    void delete(Long idReservation);

    List<Reservation> findByArticleId(Long articleId);
}
