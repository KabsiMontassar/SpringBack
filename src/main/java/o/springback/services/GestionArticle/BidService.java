package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IBidService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Bid;
import o.springback.entities.GestionUser.User;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.BidRepository;
import o.springback.repositories.GestionUserRepository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BidService implements IBidService {
    private BidRepository bidRepository;
    private AuctionRepository auctionRepository;
    private UserRepository userRepository;


    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Bid> findAll() {
        return bidRepository.findAll();
    }

    @Override
    public Bid findById(Long idBid) {
        return bidRepository.findById(idBid)
                .orElseThrow(() -> new RuntimeException("Bid not found with id " + idBid));
    }

    @Override
    public Bid save(Bid bid) {
        User currentUser = getCurrentUser();
        Auction auction = auctionRepository.findById(bid.getAuction().getId())
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if(auction.getArticle().getUser()!= null) {

            if (auction.getArticle().getUser().getIdUser().equals(currentUser.getIdUser())) {
                throw new RuntimeException("Cannot bid on your own article");
            }
        }
        if (!auction.isActive()) {
            throw new RuntimeException("Cannot place bid on inactive auction");
        }

        if (bid.getBidAmount() <= auction.getCurrentPrice()) {
            throw new RuntimeException("Bid must be higher than current price");
        }

        auction.setCurrentPrice(bid.getBidAmount());
        auctionRepository.save(auction);

        bid.setAuction(auction);
        bid.setUser(currentUser);
        bid.setBidTime(LocalDateTime.now());
        return bidRepository.save(bid);
    }

    @Override
    public Bid update(Bid bid) {
        Bid existing = bidRepository.findById(bid.getId())
                .orElseThrow(() -> new RuntimeException("Bid not found with id " + bid.getId()));
        existing.setBidAmount(bid.getBidAmount());
        existing.setBidTime(LocalDateTime.now());
        return bidRepository.save(existing);
    }

    @Override
    public void delete(Long idBid) {
        if (!bidRepository.existsById(idBid)) {
            throw new RuntimeException("Bid not found with id " + idBid);
        }
        bidRepository.deleteById(idBid);
    }

    @Override
    public List<Bid> findByArticleId(Long articleId) {
        return bidRepository.findByAuctionArticleId(articleId);
    }
    
    @Override
    public List<Bid> findByAuctionId(Long auctionId) {
        return bidRepository.findByAuctionId(auctionId);
    }
    
    @Override
    public List<Bid> findByUserId(Long userId) {
        return bidRepository.findByUserId(userId);
    }
    
    @Override
    public Bid findHighestBidByUserId(Long userId) {
        List<Bid> bids = bidRepository.findBidsByUserIdOrderByAmountDesc(userId);
        if (bids.isEmpty()) {
            throw new RuntimeException("Aucune enchère trouvée pour l'utilisateur avec l'ID: " + userId);
        }
        return bids.get(0); // Retourne le premier élément (le plus élevé)
    }


    @Override
    public List<Article> getWonArticlesByUser(Long userId) {
        return bidRepository.findWonArticlesByUserId(userId);
    }

}
