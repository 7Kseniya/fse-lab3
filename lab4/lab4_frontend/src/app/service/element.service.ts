import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { MyResponse } from "../interface/my-response"
import { Observable, catchError, tap, throwError } from 'rxjs';
import { MyElement } from '../interface/my-element';

@Injectable({
  providedIn: 'root'
})
export class ElementService {

  private readonly apiUrl = 'http://localhost:8080/api/v1/points';

  constructor(private http: HttpClient) { }

  elements(): Observable<any> {
    const url = `${this.apiUrl}/list`;

    const headers = new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem("jwt")}`);

    return this.http.get(url, { headers });
  }

  сheck_area(data: any): Observable<any> {
    const url = `${this.apiUrl}/add`;

    const headers = new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem("jwt")}`);

    return this.http.post(url, data, {headers});
  }

  clear(): Observable<any>{
    const url = `${this.apiUrl}/delete`;

    const headers = new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem("jwt")}`);

    return this.http.delete(url, { headers });
  }

}
