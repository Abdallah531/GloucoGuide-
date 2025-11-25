package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.DTO.AdminDTO;
import com.example.GlucoGuide.DTO.ArticleDTO;
import com.example.GlucoGuide.DTO.CategoryDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Entity.Article;
import com.example.GlucoGuide.Service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCategory(@PathVariable Long categoryId) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(categoryId);
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/post")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO, HttpSession session) {
        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        articleDTO.setAdminId(authenticatedAdmin.getId());
        articleDTO.setDate(new Date());
        articleDTO.setHide(false);

        ArticleDTO createdArticle = articleService.saveArticle(articleDTO); // Assuming saveArticle returns the created ArticleDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO, HttpSession session) {
        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ArticleDTO existingArticle = articleService.getArticleById(id);
        if (existingArticle == null) {
            return ResponseEntity.notFound().build();
        }

        articleDTO.setArticleId(id);
        articleDTO.setAdminId(authenticatedAdmin.getId());
        articleDTO.setDate(new Date());

        ArticleDTO updatedArticle = articleService.saveArticle(articleDTO); // Assuming saveArticle updates and returns the updated ArticleDTO
        return ResponseEntity.ok(updatedArticle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        if (articleDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articleDTO);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = articleService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, HttpSession session) {
        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        articleService.deleteArticleById(id);
        return ResponseEntity.ok("Article Deleted successfully");
    }

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<?> uploadProfilePhoto(@PathVariable Long id,@RequestParam("photo") MultipartFile photo,HttpSession session) throws IOException {
        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Article article = articleService.updateArticlePhoto(id, photo);
            return ResponseEntity.ok(article);
        } catch (Exception e){
            return ResponseEntity.ok("Photo uploaded failed");
        }
    }
}
