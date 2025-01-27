import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { Theme } from '../interfaces/theme.interface';

@Injectable({
  providedIn: 'root',
})
export class ThemesService {
  private userApiUrl = `${environment.apiBaseUrl}api/`;

  private themesSubject = new BehaviorSubject<Theme[]>([]);
  themes$ = this.themesSubject.asObservable();

  constructor(private httpClient: HttpClient) {}

  loadThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.userApiUrl + 'themes').pipe(
      tap((themes) => {
        this.themesSubject.next(themes);
      })
    );
  }

  getAllThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.userApiUrl + 'themes');
  }

}
