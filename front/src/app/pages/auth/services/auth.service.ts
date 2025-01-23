import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, switchMap, BehaviorSubject } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { UserInformation } from 'src/app/interfaces/userInformation.interface';
import { LoginResponse } from '../interfaces/loginResponse.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = environment.apiBaseUrl + 'api/auth';
  private tokenKey = 'authToken';

  private tokenSubject = new BehaviorSubject<string | null>(this.getToken());
  private isAuthSubject = new BehaviorSubject<boolean>(this.hasToken());
  private userInfoSubject = new BehaviorSubject<UserInformation | null>(null);

  public token$ = this.tokenSubject.asObservable();
  public isAuthenticated$ = this.isAuthSubject.asObservable();
  public userInfo$ = this.userInfoSubject.asObservable();

  constructor(private httpClient: HttpClient) {
    if (this.hasToken()) {
      this.loadUserInfo();
    }
  }

  private loadUserInfo(): void {
    this.getUserInfo().subscribe({
      next: (userInfo) => {
        this.userInfoSubject.next(userInfo);
        console.log('USER INFO :',userInfo)
      },
      /*error: () => {
        this.removeToken();
      },*/
    });
  }

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
          console.log('USER INFO :',userInfo);
          this.userInfoSubject.next(userInfo);
        })
      );
  }

  public getUserInfo(): Observable<UserInformation> {
    const token = this.getToken();
    if (!token) {
      throw new Error('No token found');
    }

    return this.httpClient.get<UserInformation>(
      `${environment.apiBaseUrl}api/me`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
  }

  private saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.tokenSubject.next(token);
    this.isAuthSubject.next(true);
  }

  public getToken(): string | null {
    console.log('GET TOKEN', localStorage.getItem(this.tokenKey));
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
