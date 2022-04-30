import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Message} from "../models/message";
import {Post} from "../models/post";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) {
    }

    changeNumber(number: string): Observable<Message> {
        return this.http.post<Message>("/api/user/edit/phoneNumber", number);
    }

    changeEmail(email: string): Observable<Message> {
        return this.http.post<Message>("/api/user/edit/email", email);
    }

    changePassword(password: string): Observable<Message> {
        return this.http.post<Message>("/api/user/edit/password", password);
    }

    getMyPosts(): Observable<Post[]> {
        return this.http.get<Post[]>('/api/user/posts');
    }
}
