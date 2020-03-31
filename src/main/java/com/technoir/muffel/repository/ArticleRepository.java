package com.technoir.muffel.repository;

import com.technoir.muffel.domain.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findByTag(String tag);
}
