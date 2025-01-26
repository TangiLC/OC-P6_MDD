import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Article } from '../interfaces/article.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private baseUrl = `${environment.apiBaseUrl}api/article`;

  constructor(private httpClient: HttpClient) {}

  getArticleById(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.baseUrl}/${id}`);
  }
}
