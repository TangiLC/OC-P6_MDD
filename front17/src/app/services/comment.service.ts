import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private userApiUrl = `${environment.apiBaseUrl}api/`;

  constructor(private httpClient: HttpClient) {}

  getPictureFromUsername(username: string): Observable<string> {
    return this.httpClient
      .get<{ id: number; email: string; username: string; picture: string }>(
        `${this.userApiUrl}user/${username}`
      )
      .pipe(map((user) => user.picture));
  }
}
