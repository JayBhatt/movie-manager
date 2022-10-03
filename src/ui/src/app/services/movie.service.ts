import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Api } from '../models/api';
import { environment } from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private readonly client: HttpClient;
  public static readonly IMAGE_URL: string = environment.tmdbImageUrl;

  private options = {
    headers: Api.HEADERS
  }

  constructor(httpClient: HttpClient) {
    this.client = httpClient;
  }

  public fetch(page: number): Observable<any> {
    return this.client.get(
      Api.buildResource('movies', page),
      this.options
    );
  }

  public fetchGenre(id: number): Observable<any> {
    return this.client.get(
      Api.buildResource('genre', id),
      this.options
    );
  }

}
