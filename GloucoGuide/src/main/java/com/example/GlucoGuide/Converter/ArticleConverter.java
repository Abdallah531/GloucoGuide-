package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.ArticleDTO;
import com.example.GlucoGuide.Entity.Article;
import com.example.GlucoGuide.Repository.CategoryRepository;
import com.example.GlucoGuide.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverter {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdminService adminService;

    public ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(article.getArticleId());
        articleDTO.setName(article.getName());
        articleDTO.setDate(article.getDate());
        articleDTO.setContent(article.getContent());
        articleDTO.setHide(article.isHide());
        articleDTO.setCategoryId(article.getCategory().getCategoryId());
        articleDTO.setAdminId(article.getAdmin().getId());
        articleDTO.setCategoryName(article.getCategory().getCategory());
        articleDTO.setArticlePhoto(article.getArticlePhoto());
        return articleDTO;
    }

    public Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setArticleId(articleDTO.getArticleId());
        article.setName(articleDTO.getName());
        article.setDate(articleDTO.getDate());
        article.setContent(articleDTO.getContent());
        article.setHide(articleDTO.isHide());
        article.setCategory(categoryRepository.findById(articleDTO.getCategoryId()).orElse(null));
        article.setAdmin(adminService.getAdminById(articleDTO.getAdminId()));
        article.setArticlePhoto(articleDTO.getArticlePhoto());
        return article;
    }
}