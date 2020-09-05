package com.technoir.levelup.service;

import com.technoir.levelup.model.Article;
import com.technoir.levelup.model.User;

import java.util.List;

public interface ArticleService {

    List<Article> getAll();

    Article findById(Long id);

    List<Article> getAllByAuthorUsername(String username);

}
