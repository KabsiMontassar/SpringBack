    package o.springback.controller.GestionArticle;
    import lombok.RequiredArgsConstructor;
    import o.springback.Interfaces.GestionArticle.IBidService;
    import o.springback.Interfaces.GestionArticle.IReservationService;
    import o.springback.entities.GestionArticle.Article;
    import o.springback.entities.GestionArticle.Bid;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    @RestController
    @RequestMapping("/api/bids")
    @RequiredArgsConstructor
    public class BidController {

        private final IBidService bidService;

        @GetMapping
        public ResponseEntity<List<Bid>> getAllBids() {
            return ResponseEntity.ok(bidService.findAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Bid> getBidById(@PathVariable Long id) {
            return ResponseEntity.ok(bidService.findById(id));
        }

            @PostMapping
            public ResponseEntity<Bid> createBid(@RequestBody Bid bid) {
                Bid saved = bidService.save(bid);
                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }

            @PutMapping("/{id}")
            public ResponseEntity<Bid> updateBid(@PathVariable Long id, @RequestBody Bid bid) {
                bid.setId(id);
                Bid updated = bidService.update(bid);
                return ResponseEntity.ok(updated);
            }

            @DeleteMapping("/{id}")
            public ResponseEntity<Void> deleteBid(@PathVariable Long id) {
                bidService.delete(id);
                return ResponseEntity.noContent().build();
            }

            @GetMapping("/by-article/{articleId}")
            public ResponseEntity<List<Bid>> getBidsByArticleId(@PathVariable Long articleId) {
                List<Bid> bids = bidService.findByArticleId(articleId);
                return ResponseEntity.ok(bids);
            }

            @GetMapping("/auction/{auctionId}")
            public ResponseEntity<List<Bid>> getBidsByAuctionId(@PathVariable Long auctionId) {
                List<Bid> bids = bidService.findByAuctionId(auctionId);
                return ResponseEntity.ok(bids);
            }

            @GetMapping("/user/{userId}/highest")
            public ResponseEntity<Bid> getHighestBidByUserId(@PathVariable Long userId) {
                try {
                    return ResponseEntity.ok(bidService.findHighestBidByUserId(userId));
                } catch (RuntimeException e) {
                    return ResponseEntity.notFound().build();
                }
            }

            @GetMapping("/user/{userId}")
            public ResponseEntity<List<Bid>> getBidsByUserId(@PathVariable Long userId) {
                return ResponseEntity.ok(bidService.findByUserId(userId));
            }
        @GetMapping("/user/{userId}/won-articles")
        public ResponseEntity<List<Article>> getWonArticlesByUser(@PathVariable Long userId) {
            return ResponseEntity.ok(bidService.getWonArticlesByUser(userId));
        }

    }
