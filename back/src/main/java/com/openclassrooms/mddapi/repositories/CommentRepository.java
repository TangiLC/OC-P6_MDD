package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByAuthorId(Long authorId);

  List<Comment> findByArticleId(Long articleId);
}
