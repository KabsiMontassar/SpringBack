package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.Bid;

import java.util.List;

public interface IBidService {
    List<Bid> findAll();
    Bid findById(Long idBid);
    Bid save(Bid bid);
    Bid update(Bid bid);
    void delete(Long idBid);
}
