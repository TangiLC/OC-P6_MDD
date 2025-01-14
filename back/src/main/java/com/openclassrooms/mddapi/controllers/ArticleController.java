package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CreateArticleDto;
import com.openclassrooms.mddapi.dto.ErrorResponse;
import com.openclassrooms.mddapi.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing articles.
 */
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Create a new article.
     *
     * @param createArticleDto the data transfer object containing article creation details
     * @return the created article as an ArticleDto
     */
    @Operation(summary = "Create a new article", description = "Adds a new article with the provided details.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Article created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ArticleDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(
            @RequestBody @Valid CreateArticleDto createArticleDto
    ) {
        ArticleDto createdArticle = articleService.createArticle(createArticleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    /**
     * Get all articles associated with a specific theme.
     *
     * @param themeId the ID of the theme
     * @return a set of articles as ArticleDto objects
     */
    @Operation(summary = "Get articles by theme", description = "Retrieves all articles associated with the specified theme.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Articles retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Set.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Theme not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @GetMapping("/theme/{themeId}")
    public ResponseEntity<Set<ArticleDto>> getByTheme(@PathVariable Long themeId) {
        Set<ArticleDto> articles = articleService.getArticlesByTheme(themeId);
        return ResponseEntity.ok(articles);
    }

    /**
     * Get all articles created by a specific author.
     *
     * @param authorId the ID of the author
     * @return a set of articles as ArticleDto objects
     */
    @Operation(summary = "Get articles by author", description = "Retrieves all articles created by the specified author.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Articles retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Set.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Author not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @GetMapping("/author/{authorId}")
    public ResponseEntity<Set<ArticleDto>> getByAuthor(@PathVariable Long authorId) {
        Set<ArticleDto> articles = articleService.getArticlesByAuthor(authorId);
        return ResponseEntity.ok(articles);
    }

    /**
     * Update an article by its ID.
     *
     * @param id              the ID of the article to update
     * @param updateArticleDto the data transfer object containing updated article details
     * @return the updated article as an ArticleDto
     */
    @Operation(summary = "Update an article", description = "Updates an existing article with the provided details.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Article updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ArticleDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Article not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(
            @PathVariable Long id,
            @RequestBody @Valid ArticleDto updateArticleDto
    ) {
        ArticleDto updatedArticle = articleService.updateArticle(id, updateArticleDto);
        return ResponseEntity.ok(updatedArticle);
    }

    /**
     * Delete an article by its ID.
     *
     * @param id the ID of the article to delete
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Delete an article", description = "Deletes an article by its ID.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Article deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Article not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
