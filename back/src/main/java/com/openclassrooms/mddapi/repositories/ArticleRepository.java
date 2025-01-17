package com.openclassrooms.mddapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @SuppressWarnings("null")
  @Override
  Optional<Article> findById(Long articleId);

  List<Article> findByAuthorId(Long authorId);

  @Query("SELECT a FROM Article a JOIN a.themes t WHERE t.id = :themeId")
  List<Article> findArticlesByThemeId(@Param("themeId") Long themeId);
}
