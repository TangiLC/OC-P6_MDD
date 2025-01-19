package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Comment;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @SuppressWarnings("null")
  
  @Query("SELECT t FROM Theme t JOIN t.articles a WHERE a.id = :articleId")
  Set<Theme> findThemesByArticleId(@Param("articleId") Long articleId);

  @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.author WHERE c.article.id = :articleId")
  Set<Comment> findCommentsByArticleId(@Param("articleId") Long articleId);

  @Query("SELECT a FROM Article a LEFT JOIN FETCH a.author WHERE a.id = :id")
  Optional<Article> findArticleById(@Param("id") Long id);

  /*@Query("""
        SELECT a FROM Article a
        JOIN  a.themes t
        LEFT JOIN  a.author auth
        LEFT JOIN  a.comments c
        WHERE a.id = :id
    """)
    Optional<Article> findArticleById(@Param("id") Long id);*/

    @Query(value = """
            SELECT COUNT(t.id) 
            FROM Article a 
            JOIN a.themes t 
            WHERE a.id = :articleId
            """)
    Long debugCountThemesByArticle(@Param("articleId") Long articleId);


}
