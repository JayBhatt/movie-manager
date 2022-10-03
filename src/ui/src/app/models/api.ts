import { HttpHeaders } from '@angular/common/http';
import { environment } from "../environments/environment";

export class Api {

    static readonly URL: string = environment.apiUrl;

    static readonly HEADERS:HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json',  });

    static readonly ERROR: string = "An error occurred while processing your request. Please try again after sometime.";

    static readonly RESOURCE: any = {
        "login": "auth/login",
        "user": "auth/user",
        "config": "config",
        "register" : "user",
        "movies": "movie/popular",
        "genre": "movie/genre"
    };

    static buildResource(resource: string, param: number | undefined = undefined): string {
        // All services except just one parameter for now but this can be extended to include others.
        const endpoint: string = `${Api.URL}${Api.RESOURCE[resource]}`;
        if (param) {
            return `${endpoint}/${param}`;
        }
        return `${endpoint}`;
    }

}
