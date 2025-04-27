package o.springback.controller.GestionArticle;
import lombok.RequiredArgsConstructor;
import o.springback.Interfaces.GestionArticle.IArticleService;
import o.springback.entities.GestionArticle.Article;
import o.springback.entities.GestionArticle.PaymentArticle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article saved = articleService.save(article);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        article.setId(id);
        Article updated = articleService.update(article);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Article>> getArticlesByType(@PathVariable PaymentArticle.PaymentType type) {
        return ResponseEntity.ok(articleService.findByTypeArticle(type));
    }

    @GetMapping("/articlesearch")
    public List<Article> searchArticle(@RequestParam String title) {
        return articleService.searchByTitle(title);
    }

    @GetMapping("/by-availability-and-type")
    public ResponseEntity<List<Article>> getArticlesByAvailabilityAndType(
            @RequestParam boolean available,
            @RequestParam PaymentArticle.PaymentType type) {
        return ResponseEntity.ok(articleService.findByIsAvailableAndTypeArticle(available, type));
    }


}

