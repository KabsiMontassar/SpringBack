package o.springback.services.GestionArticle;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IAuctionService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Bid;
import o.springback.repositories.GestionArticle.ArticleRepository;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.BidRepository;
import o.springback.repositories.GestionArticle.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AuctionService implements IAuctionService {

    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;
    private PaymentRepository paymentRepository;
    private ArticleRepository articleRepository;

    @Override
    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

 /*   @Override
    public Auction findById(Long idAuction) {
        return auctionRepository.findById(idAuction)
                .orElseThrow(() -> new RuntimeException("Auction not found with id: " + idAuction));
    }*/
 @Override
 public Auction findById(Long id) {
     return auctionRepository.findByIdWithArticle(id)
             .orElseThrow(() -> new RuntimeException("Auction not found with id: " + id));
 }

    @Override
    public Auction save(Auction auction) {
        auction.setStartTime(LocalDateTime.now());
        auction.setCurrentPrice(auction.getStartPrice());
        auction.setActive(true);

        // Lier les enchères
        if (auction.getBids() != null) {
            for (Bid bid : auction.getBids()) {
                bid.setAuction(auction);
            }
        }

        // Lier le paiement s'il existe déjà (rare à la création)
        if (auction.getPayment() != null) {
            auction.getPayment().setAuction(auction);
            paymentRepository.save(auction.getPayment());
        }

        return auctionRepository.save(auction);
    }

    @Override
    public Auction update(Auction auction) {
        Auction existing = findById(auction.getId());

        existing.setStartPrice(auction.getStartPrice());
        existing.setCurrentPrice(auction.getCurrentPrice());
        existing.setStartTime(auction.getStartTime());
        existing.setEndTime(auction.getEndTime());
        existing.setActive(auction.isActive());

        if (auction.getBids() != null) {
            for (Bid bid : auction.getBids()) {
                bid.setAuction(existing);
            }
            bidRepository.saveAll(auction.getBids());
            existing.setBids(auction.getBids());
        }

        if (auction.getPayment() != null) {
            auction.getPayment().setAuction(existing);
            paymentRepository.save(auction.getPayment());
            existing.setPayment(auction.getPayment());
        }

        return auctionRepository.save(existing);
    }

    @Override
    public void delete(Long idAuction) {
        Auction auction = findById(idAuction);

        if (auction.getBids() != null) {
            bidRepository.deleteAll(auction.getBids());
        }

        if (auction.getPayment() != null) {
            paymentRepository.delete(auction.getPayment());
        }



   Article article = articleRepository.findByAuctionId(idAuction);


        if(article != null) {
            article.setAuction(null);
            article.setAvailable(true);
            articleRepository.save(article);
        }

        auctionRepository.delete(auction);
    }

    public List<Auction> getTop5AuctionsByBids() {
        return auctionRepository.findTop5ByBidsCount();
    }



    @Override
    public List<Auction> findByArticleId(Long articleId) {
        return auctionRepository.findByArticleId(articleId);
    }


    @Override
    public void finalizeAuction(Long auctionId) {
        Auction auction = findById(auctionId);
        if (!auction.isActive()) {
            throw new RuntimeException("Auction is already finished");
        }

        auction.setActive(false);
        auction.setEndTime(LocalDateTime.now());

        // Trouver l'enchère gagnante
        Bid winningBid = auction.getBids().stream()
                .max(Comparator.comparing(Bid::getBidAmount))
                .orElse(null);

        if (winningBid != null) {
            Article article = auction.getArticle();
            // Assigner l'article au gagnant
            article.setUser(winningBid.getUser());
            article.setAvailable(false);
            articleRepository.save(article);
        }

        auctionRepository.save(auction);
    }


}
