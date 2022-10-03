import {Injectable} from '@angular/core';
import {User} from '../models/user';
import {StorageService} from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public static readonly TTL: number = 30;
  public static readonly CONFIG_NAME: string = "user";
  private readonly storageService: StorageService;

  constructor(storageService: StorageService) {
    this.storageService = storageService;
  }

  public getUser(): User {
    const user = this.storageService.get(UserService.CONFIG_NAME) as User;
    if (Object.keys(user).length !== 0) {
      return user;
    }
    return {} as User;
  }

  public saveUser(user: User): void {
    /*
    * @Note: Authorisation is only valid for 30 minutes, after 30 minutes force to get a new token.
    */
    const now = new Date();
    now.setMinutes(now.getMinutes() + UserService.TTL);
    user.tokenExpiry = Math.floor(now.valueOf() / (1000 * 60 * 60));
    this.storageService.save(UserService.CONFIG_NAME, user);
  }
}
