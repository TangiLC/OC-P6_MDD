package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> findByAuthorId(Long authorId);

  @Query("SELECT a FROM Article a JOIN a.themes t WHERE t.id = :themeId")
  List<Article> findArticlesByThemeId(@Param("themeId") Long themeId);
}
