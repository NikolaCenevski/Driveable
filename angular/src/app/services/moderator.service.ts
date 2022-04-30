import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../models/post";
import {ReportPost} from "../models/reportPost";
import {Message} from "../models/message";

@Injectable({
    providedIn: 'root'
})
export class ModeratorService {

    constructor(private http: HttpClient) {
    }

    getAllReportPosts(): Observable<Post[]> {
        return this.http.get<Post[]>('/api/moderator/posts')
    }

    getPost(postId: number): Observable<ReportPost> {
        return this.http.get<ReportPost>(`/api/moderator/post/${postId}`)
    }

    approve(postId: number): Observable<Message> {
        return this.http.get<Message>(`/api/moderator/post/${postId}/approve`)
    }

    delete(postId: number): Observable<Message> {
        return this.http.get<Message>(`/api/moderator/post/${postId}/delete`)
    }
}
