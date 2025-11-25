package com.example.GlucoGuide.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    private Long articleId;
    private String name;
    private Date date;
    private String content;
    private boolean hide;
    private Long categoryId;
    private String categoryName;
    private Long adminId;
    private byte[] articlePhoto;
}