import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Jwt} from "../models/jwt";
import {Message} from "../models/message";

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    constructor(private http: HttpClient) {
    }

    login(username: string, password: string): Observable<Jwt> {
        return this.http.post<Jwt>('/api/auth/authenticate', {
            userName: username,
            password: password,
        })
    }

    register(username: any, password: any, email: any, phoneNumber: any, name: any, surname: any): Observable<Message> {
        return this.http.post<Message>('/api/auth/register', {
            username: username,
            password: password,
            email: email,
            phoneNumber: phoneNumber,
            name: name,
            surname: surname,
        })
    }
}
