import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MyResponse } from "../interface/my-response"
import { Observable, catchError, tap, throwError } from 'rxjs';
import { MyUser } from '../interface/my-user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) { }


  loginUser(data: any): Observable<any> {
    const url = `${this.apiUrl}/authentication/login`;

    return this.http.post(url, data);
  }

  registerUser(data: any): Observable<any> {
    const url = `${this.apiUrl}/authentication/register`;

    return this.http.post(url, data);
  }
}