import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-article-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatDividerModule, MatIconModule],
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent {
  @Input() article: {
    id: number;
    title: string;
    content: string;
    createdAt: string;
    updatedAt: string;
    authorUsername: string;
    authorPicture:string;
    themeIds: number[];
  } | null = null;
  

  themes: { [key: number]: string } = {
    1: 'Angular',
    2: 'BDD',
    3: 'Spring',
    4: 'Tests',
    5: 'UX/UI',
    6: 'Style'
  };
  
  getThemeNames(): string[] {
    if (!this.article?.themeIds) return [];
    return this.article.themeIds.map(id => this.themes[id] || 'Inconnu');
  }
}
