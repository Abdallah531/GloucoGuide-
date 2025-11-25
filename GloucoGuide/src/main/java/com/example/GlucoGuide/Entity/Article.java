package com.example.GlucoGuide.Entity;


import com.example.GlucoGuide.Repository.ArticleRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false ,columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(nullable = false)
    private boolean hide;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "article_photo",columnDefinition = "MEDIUMBLOB" )
    private byte[] articlePhoto;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "category_Id",referencedColumnName = "categoryId", nullable = false)
    private Category category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "admin_Id", referencedColumnName = "id", nullable = false)
    private Admin admin;


    public Article(long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }


}
