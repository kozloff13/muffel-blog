package com.technoir.muffel.controllers;
import com.technoir.muffel.domain.Article;
import com.technoir.muffel.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Article> articles = articleRepository.findAll();
        model.put("articles", articles);
        return "main";
    }

    @PostMapping("/main")
    public String addArticle(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Article article = new Article(text, tag);
        articleRepository.save(article);
        Iterable<Article> articles = articleRepository.findAll();
        model.put("articles", articles);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Article> articles;
        if (filter != null || !filter.isEmpty()) {
            articles = articleRepository.findByTag(filter);
        } else {
            articles = articleRepository.findAll();
        }

        model.put("articles", articles);
        return "main";
    }

}
