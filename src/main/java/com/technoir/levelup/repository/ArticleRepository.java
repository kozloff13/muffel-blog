package com.technoir.levelup.repository;

import com.technoir.levelup.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
