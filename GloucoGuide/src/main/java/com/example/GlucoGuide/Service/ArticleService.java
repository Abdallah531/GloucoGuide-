package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.Converter.ArticleConverter;
import com.example.GlucoGuide.Converter.CategoryConverter;
import com.example.GlucoGuide.DTO.ArticleDTO;
import com.example.GlucoGuide.DTO.CategoryDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Entity.Article;
import com.example.GlucoGuide.Entity.Notification;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Entity.UserDetails;
import com.example.GlucoGuide.Repository.ArticleRepository;
import com.example.GlucoGuide.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.GlucoGuide.Repository.CategoryRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleConverter articleConverter;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public List<ArticleDTO> getArticlesByCategory(Long categoryId) {
        return articleRepository.findByCategoryCategoryId(categoryId)
                .stream()
                .map(articleConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return article != null ? articleConverter.convertToDTO(article) : null;
    }


    public ArticleDTO saveArticle(ArticleDTO articleDTO) {
        Article article = articleConverter.convertToEntity(articleDTO);
        Article savedArticle = articleRepository.save(article);
        notifyUsers(savedArticle);
        return articleConverter.convertToDTO(savedArticle);
    }

    private void notifyUsers(Article article) {
        List<UserAccountDTO> users = userService.getAllUsers();
        String message = "New article posted: " + article.getName();
        for (UserAccountDTO user : users) {
            notificationService.createNotification(user, message);
        }
    }


    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public Article updateArticlePhoto(Long id, MultipartFile photo) throws IOException {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        article.setArticlePhoto(photo.getBytes());
        articleRepository.save(article);
        Article article1 = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        return article1;
    }
}