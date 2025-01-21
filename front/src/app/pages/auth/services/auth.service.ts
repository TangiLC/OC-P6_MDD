import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, switchMap, BehaviorSubject } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { UserInformation } from 'src/app/interfaces/userInformation.interface';
import { LoginResponse } from '../interfaces/loginResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'api/auth';
  private tokenKey = 'authToken';

  private tokenSubject = new BehaviorSubject<string | null>(this.getToken());
  private isAuthSubject = new BehaviorSubject<boolean>(this.hasToken())
  public token$ = this.tokenSubject.asObservable();

  private userInfoSubject = new BehaviorSubject<UserInformation | null>(null);
  public userInfo$ = this.userInfoSubject.asObservable();
  public isAuthenticated$ = this.isAuthSubject.asObservable();


  constructor(private httpClient: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<UserInformation> {
    return this.httpClient
      .post<LoginResponse>(`${this.pathService}/login`, loginRequest)
      .pipe(
        tap((response: LoginResponse) => {
          if (response.token) {
            this.saveToken(response.token);
          }
        }),
        switchMap(() => this.getUserInfo()),
        tap((userInfo: UserInformation) => {
          this.userInfoSubject.next(userInfo);
        })
      );
  }

  public getUserInfo(): Observable<UserInformation> {
    const token = this.getToken();
    if (!token) {
      throw new Error('No token found');
    }

    return this.httpClient.get<UserInformation>(`${this.pathService}/me`, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  private saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.tokenSubject.next(token);
    this.isAuthSubject.next(true);
  }

  public getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  public removeToken(): void {
    localStorage.removeItem(this.tokenKey);
    this.tokenSubject.next(null);
    this.isAuthSubject.next(false);
  }

  public hasToken(): boolean {
    return !!this.getToken();
  }
}
