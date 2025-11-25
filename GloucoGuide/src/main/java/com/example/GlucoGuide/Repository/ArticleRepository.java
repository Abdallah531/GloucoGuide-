package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategoryCategoryId(Long categoryId);



}
