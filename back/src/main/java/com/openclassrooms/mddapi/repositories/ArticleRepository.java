package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @SuppressWarnings("null")
  //@Override
  //Optional<Article> findById(Long articleId);

  //List<Article> findByAuthorId(Long authorId);

  /*@Query("SELECT a FROM Article a LEFT JOIN FETCH a.themes t WHERE a.id = :id")
  Optional<Article> findArticleById(@Param("id") Long id);*/

  @Query("""
        SELECT DISTINCT a FROM Article a
        LEFT JOIN FETCH a.themes
        LEFT JOIN FETCH a.author
        LEFT JOIN FETCH a.comments
        WHERE a.id = :id
    """)
    Optional<Article> findArticleById(@Param("id") Long id);


}
