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
    themeIds: number[];
  } | null = null;
  
}
