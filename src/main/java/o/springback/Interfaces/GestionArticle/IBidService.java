package o.springback.Interfaces.GestionArticle;

import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Bid;
import o.springback.entities.GestionUser.User;

import java.util.List;

public interface IBidService {
    User getCurrentUser();

    List<Bid> findAll();
    Bid findById(Long idBid);
    Bid save(Bid bid);
    Bid update(Bid bid);
    void delete(Long idBid);

    List<Bid> findByArticleId(Long articleId);

    List<Bid> findByAuctionId(Long auctionId);
    
    List<Bid> findByUserId(Long userId);
    
    Bid findHighestBidByUserId(Long userId);

    List<Article> getWonArticlesByUser(Long userId);
}
