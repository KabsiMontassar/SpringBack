package o.springback.Interfaces.GestionArticle;


import o.springback.entities.GestionArticle.Auction;

import java.util.List;

public interface IAuctionService {
    List<Auction> findAll();
    Auction findById(Long idAuction);
    Auction save(Auction auction);
    Auction update(Auction auction);
    void delete(Long idAuction);
}
