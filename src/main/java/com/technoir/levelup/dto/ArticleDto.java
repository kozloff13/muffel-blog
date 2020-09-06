package com.technoir.levelup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technoir.levelup.model.Article;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {
    private Long id;
    private String title;
    private String body;
    private String authorUsername;
    private Date created;
    private boolean published;

    public static ArticleDto fromArticle(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setBody(article.getBody());
        articleDto.setAuthorUsername(article.getAuthor().getUsername());
        articleDto.setCreated(article.getCreated());
        articleDto.setPublished(article.isPublished());
        return articleDto;
    }
}
