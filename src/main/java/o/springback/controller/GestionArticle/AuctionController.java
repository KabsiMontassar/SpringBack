package o.springback.controller.GestionArticle;
import lombok.RequiredArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.Interfaces.GestionArticle.IAuctionService;
import o.springback.Interfaces.GestionArticle.IReservationService;
import o.springback.entities.GestionArticle.Auction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final IAuctionService auctionService;

    private final IArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.findById(id));
    }

  @PostMapping("/{id}")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction , @PathVariable Long id) {
        Auction Newauction = auctionService.save(auction);
        articleService.AffectAuctionToArticle(id, Newauction.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(Newauction);
    }

    /*   @PutMapping("/article/{articleId}/edit/{auctionId}")
     public ResponseEntity<Auction> updateAuction(@PathVariable Long articleId,
                                                  @PathVariable Long auctionId,
                                                  @RequestBody Auction auction) {
         auction.setId(auctionId);
         auction.setArticle(articleService.findById(articleId));
         Auction updated = auctionService.update(auction);
         return ResponseEntity.ok(updated);
     }*/
 @PutMapping("/article/{articleId}/edit/{auctionId}")
 public ResponseEntity<Auction> updateAuction(
         @PathVariable Long articleId,
         @PathVariable Long auctionId,
         @RequestBody Auction auction) {
     auction.setId(auctionId);
     auction.setArticle(articleService.findById(articleId));
     Auction updated = auctionService.update(auction);
     return ResponseEntity.ok(updated);
 }

    // Keep the old endpoint for backward compatibility if needed
    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuctionLegacy(
            @PathVariable Long id,
            @RequestBody Auction auction) {
        auction.setId(id);
        Auction updated = auctionService.update(auction);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/top-bid")
    public ResponseEntity<List<Auction>> getTop5AuctionsByBids() {
        return ResponseEntity.ok(auctionService.getTop5AuctionsByBids());
    }
    @GetMapping("/by-article/{articleId}")
    public ResponseEntity<List<Auction>> getAuctionsByArticleId(@PathVariable Long articleId) {
        List<Auction> auctions = auctionService.findByArticleId(articleId);
        return ResponseEntity.ok(auctions);
    }
}

