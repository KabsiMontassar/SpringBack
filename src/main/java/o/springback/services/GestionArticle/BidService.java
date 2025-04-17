package o.springback.services.GestionArticle;

import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionArticle.IBidService;
import o.springback.entities.GestionArticle.Auction;
import o.springback.entities.GestionArticle.Bid;
import o.springback.repositories.GestionArticle.AuctionRepository;
import o.springback.repositories.GestionArticle.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BidService implements IBidService {
    private BidRepository bidRepository;
    private AuctionRepository auctionRepository;

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

        Auction auction = auctionRepository.findById(bid.getAuction().getId())
                .orElseThrow(() -> new RuntimeException("Auction not found with id " + bid.getAuction().getId()));


        if (!auction.isActive()) {
            throw new RuntimeException("Cannot place bid on inactive auction");
        }


        if (bid.getBidAmount() <= auction.getCurrentPrice()) {
            throw new RuntimeException("Bid must be higher than current price");
        }

        auction.setCurrentPrice(bid.getBidAmount());
        auctionRepository.save(auction);

        bid.setAuction(auction);
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
}
