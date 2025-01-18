package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CreateArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ThemeRepository themeRepository;

  @Transactional
  public ArticleDto createArticle(CreateArticleDto createArticleDto) {
    List<Theme> themes = themeRepository.findAllById(
      createArticleDto.getThemeIds()
    );
    if (
      themes.isEmpty() || themes.size() != createArticleDto.getThemeIds().size()
    ) {
      throw new RuntimeException("One or more themes not found");
    }

    Article article = new Article();
    article.setTitle(createArticleDto.getTitle());
    article.setContent(createArticleDto.getContent());
    article.setAuthor(getAuthenticatedUser());

    Article savedArticle = articleRepository.save(article);

    Set<Theme> themeSet = new HashSet<>(themes);
    savedArticle.setThemes(themeSet);
    savedArticle = articleRepository.save(article);
    return toDto(savedArticle);
  }

  @Transactional
  public ArticleDto updateArticle(Long id, ArticleDto updateArticleDto) {
    Article article = articleRepository
      .findById(id)
      .orElseThrow(() ->
        new RuntimeException("Article not found with ID: " + id)
      );

    User authenticatedUser = getAuthenticatedUser();
    if (!article.getAuthor().getId().equals(authenticatedUser.getId())) {
      throw new AccessDeniedException("You can only modify your own articles");
    }

    List<Theme> themes = themeRepository.findAllById(
      updateArticleDto.getThemeIds()
    );
    if (
      themes.isEmpty() || themes.size() != updateArticleDto.getThemeIds().size()
    ) {
      throw new RuntimeException("One or more themes not found");
    }

    article.setTitle(updateArticleDto.getTitle());
    article.setContent(updateArticleDto.getContent());

    Set<Theme> themeSet = new HashSet<>(themes);
    article.setThemes(themeSet);

    Article updatedArticle = articleRepository.save(article);
    return toDto(updatedArticle);
  }

  public void deleteArticle(Long id) {
    Article article = articleRepository
      .findById(id)
      .orElseThrow(() ->
        new RuntimeException("Article not found with ID: " + id)
      );

    User authenticatedUser = getAuthenticatedUser();
    boolean isAuthor = article
      .getAuthor()
      .getId()
      .equals(authenticatedUser.getId());
    boolean isAdmin =
      authenticatedUser.getIsAdmin() != null && authenticatedUser.getIsAdmin();

    if (!isAuthor && !isAdmin) {
      throw new AccessDeniedException(
        "You can only delete your own articles or must be an admin"
      );
    }

    articleRepository.delete(article);
  }

  @Transactional(readOnly = true)
  public ArticleDto getArticleById(Long articleId) {
    Article article = articleRepository
      .findArticleById(articleId)
      .orElseThrow(() ->
        new RuntimeException("Article not found with ID: " + articleId)
      );

    return toDto(article);
  }

  /*public List<ArticleDto> getArticlesByAuthor(Long authorId) {
    return articleRepository
      .findByAuthorId(authorId)
      .stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }

  public List<ArticleDto> getArticlesByThemeId(Long themeId) {
    return articleRepository
      .findArticlesByThemeId(themeId)
      .stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }*/

  private User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (
      authentication != null &&
      authentication.getPrincipal() instanceof UserPrincipal
    ) {
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      return userPrincipal.getUser();
    }
    throw new RuntimeException("No authenticated user found");
  }

  @Transactional(readOnly = true)
  public ArticleDto toDto(Article article) {
    Set<Long> themeIds = article.getThemes() != null
      ? article
        .getThemes()
        .stream()
        .map(Theme::getId)
        .collect(Collectors.toSet())
      : new HashSet<>();

    Set<Long> commentIds = article.getComments() != null
      ? article
        .getComments()
        .stream()
        .map(Comment::getId)
        .collect(Collectors.toSet())
      : new HashSet<>();

    return ArticleDto
      .builder()
      .id(article.getId())
      .title(article.getTitle())
      .content(article.getContent())
      .createdAt(article.getCreatedAt())
      .updatedAt(article.getUpdatedAt())
      .authorUsername(
        article.getAuthor() != null ? article.getAuthor().getUsername() : null
      )
      .themeIds(themeIds)
      .commentIds(commentIds)
      .build();
  }
}
