package com.technoir.levelup.controller;

import com.technoir.levelup.dto.ArticleDto;
import com.technoir.levelup.model.Article;
import com.technoir.levelup.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    @Value("${locale}")
    private String locale;
    private ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(locale != null ? locale : "ru"));

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/articles")
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<Article> articles = articleService.getAll();
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ArticleDto> result = new ArrayList<>();
        for (Article article : articles) {
            result.add(ArticleDto.fromArticle(article));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
