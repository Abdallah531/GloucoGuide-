package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Entity.Article;
import com.example.GlucoGuide.Service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/articles")
public class ArticleSearchController {


    @Autowired
    private ArticleSearchService articleSearchService;


    @PostMapping("/search")
    public ResponseEntity<List<Article>> searchArticles(@RequestParam String query) {
        List<Article> searchResults = articleSearchService.searchArticles(query);
        if (searchResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(searchResults);
    }
}
