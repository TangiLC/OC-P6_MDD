import { MatProgressBarModule } from '@angular/material/progress-bar';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { ArticleDetailComponent } from '../../components/article-detail/article-detail.component';
import { CommentDetailComponent } from '../../components/comment-detail/comment-detail.component';
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { Article } from '../../interfaces/article.interface';
import { CreateCommentComponent } from '../../components/create-comment/create-comment.component';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatProgressBarModule,
    ArticleDetailComponent,
    CommentDetailComponent,
    BackArrowComponent,
    CreateCommentComponent,
  ],
})
export class ArticleComponent implements OnInit {
  article$: Observable<Article | null> = of(null);
  article: Article | null = null;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.article$ = this.articleService.getArticleById(+id);

      this.article$.subscribe((data) => {
        this.article = data;
        console.log('ARTICLE',this.article);
        this.article?.comments.sort((a, b) => {
          const dateA = new Date(a.createdAt.replace(' ', 'T')).getTime();
          const dateB = new Date(b.createdAt.replace(' ', 'T')).getTime();
          return dateB - dateA; 
        });
      });
    }
  }

  refreshArticle(): void {
    if (this.article?.id) {
      this.article$ = this.articleService.getArticleById(this.article.id);
      this.article$.subscribe((data) => (this.article = data));
    }
  }
}
