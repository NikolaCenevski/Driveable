import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    constructor(private http: HttpClient) {
    }

    login(username: string, password: string): Observable<any> {
        return this.http.post('/api/auth/authenticate', {
            userName: username,
            password: password,
        })
    }

    register(username: any, password: any, email: any, phoneNumber: any, name: any, surname: any) {
        return this.http.post('/api/auth/register', {
            username: username,
            password: password,
            email: email,
            phoneNumber: phoneNumber,
            name: name,
            surname: surname,
        })
    }
}
