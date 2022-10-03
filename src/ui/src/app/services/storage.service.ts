import { Injectable } from '@angular/core';
import { Api } from '../models/api';
import { Buffer } from 'buffer';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  clean(): void {
    window.sessionStorage.clear();
  }

  public save(key: string, val: any) {
    const encodedVal = Buffer.from(JSON.stringify(val), 'utf8').toString('base64');
    window.sessionStorage.setItem(key, encodedVal);
  }

  public get(key: string): string {
    try {
      const value: string | null = window.sessionStorage.getItem(key);
      if (!value) {
        return "";
      }
      const decodedVal = Buffer.from(value, 'base64').toString('utf8');
      return JSON.parse(decodedVal);
    } catch(error) {
      throw new Error(Api.ERROR);
    }
  }

  public remove(key: string): void {
    window.sessionStorage.removeItem(key);
  }

}
