package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
  /*@Query(
    "SELECT t FROM THEME t WHERE t.id IN (SELECT ar.themeId FROM ArticleRelation ar WHERE ar.articleId = :articleId)"
  )
  List<Theme> findByArticleId(@Param("articleId") Long articleId);*/
}
