package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServicesUtils {

  private final ArticleRepository articleRepository;

  public ServicesUtils(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public User getAuthenticatedUser() {
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

  public void validateAdminOrAuthenticatedUser(User articleAuthor) {
    User authenticatedUser = getAuthenticatedUser();
    boolean isAuthor = articleAuthor.getId().equals(authenticatedUser.getId());
    boolean isAdmin =
      authenticatedUser.getIsAdmin() != null && authenticatedUser.getIsAdmin();

    if (!isAuthor && !isAdmin) {
      throw new AccessDeniedException(
        "You can only modify your own articles or must be an admin"
      );
    }
  }

  public Article validateArticleId(Long articleId) {
    return articleRepository
      .findArticleById(articleId)
      .orElseThrow(() ->
        new RuntimeException("Article not found with ID: " + articleId)
      );
  }
}
