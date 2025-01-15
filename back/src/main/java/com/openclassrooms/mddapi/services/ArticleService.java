package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CreateArticleDto;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;

  public ArticleDto getById(Long articleId) {
    Optional<Article> article = articleRepository.findById(articleId);
    return article
      .map(this::mapToDto)
      .orElseThrow(() ->
        new NotFoundException("Article not found with ID: " + articleId)
      );
  }

  public Set<ArticleDto> getArticlesByTheme(Long themeId) {
    List<Article> articles = articleRepository.findArticlesByThemeId(themeId);
    return articles.stream().map(this::mapToDto).collect(Collectors.toSet());
  }

  public Set<ArticleDto> getArticlesByAuthor(Long authorId) {
    List<Article> articles = articleRepository.findByAuthorId(authorId);
    return articles.stream().map(this::mapToDto).collect(Collectors.toSet());
  }

  @Transactional
  public ArticleDto createArticle(CreateArticleDto createArticleDto) {
    // get author Id
    User author = userRepository
      .findById(createArticleDto.getAuthorId())
      .orElseThrow(() ->
        new NotFoundException(
          "Author id not found: " + createArticleDto.getAuthorId()
        )
      );

    // get themes Ids
    List<Theme> themes = themeRepository.findAllById(
      createArticleDto.getThemeIds()
    );
    if (
      themes.isEmpty() || themes.size() != createArticleDto.getThemeIds().size()
    ) {
      throw new NotFoundException("One or more themes not found");
    }

    // Create new article
    Article article = new Article();
    article.setTitle(createArticleDto.getTitle());
    article.setContent(createArticleDto.getContent());
    article.setAuthor(author);
    article.setThemes(new HashSet<>(themes));

    // Save in bdd
    Article savedArticle = articleRepository.save(article);

    // Convert to Dto and return
    return mapToDto(savedArticle);
  }

  @Transactional
  public ArticleDto updateArticle(Long id, ArticleDto updateArticleDto) {
    Article article = articleRepository
      .findById(id)
      .orElseThrow(() -> new NotFoundException("Article not found: " + id));

    article.setTitle(updateArticleDto.getTitle());
    article.setContent(updateArticleDto.getContent());
    articleRepository.save(article);

    return mapToDto(article);
  }

  @Transactional
  public void deleteArticle(Long id) {
    if (!articleRepository.existsById(id)) {
      throw new NotFoundException("Article not found: " + id);
    }
    articleRepository.deleteById(id);
  }

  private ArticleDto mapToDto(Article article) {
    Set<Long> themeIds = article
      .getThemes()
      .stream()
      .map(Theme::getId)
      .collect(Collectors.toSet());

    return ArticleDto
      .builder()
      .id(article.getId())
      .title(article.getTitle())
      .content(article.getContent())
      .createdAt(article.getCreatedAt())
      .updatedAt(article.getUpdatedAt())
      .authorUsername(article.getAuthor().getUsername())
      .themesSet(themeIds)
      .commentsSet(Set.of())
      .build();
  }
}
