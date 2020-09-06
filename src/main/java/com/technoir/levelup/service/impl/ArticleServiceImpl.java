package com.technoir.levelup.service.impl;

import com.technoir.levelup.model.Article;
import com.technoir.levelup.repository.ArticleRepository;
import com.technoir.levelup.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Article> getAllByAuthorUsername(String username) {
        return null;
    }
}
