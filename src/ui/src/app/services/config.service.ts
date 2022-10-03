import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Api } from '../models/api';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private readonly client: HttpClient;

  private options = {
    headers: Api.HEADERS
  }

  constructor(httpClient: HttpClient) {
    this.client = httpClient;
  }

  public fetch(): Observable<any> {
    return this.client.get(
      Api.buildResource('config'),
      this.options
    );
  }
  
}
