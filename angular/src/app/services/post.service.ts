import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../models/post";

@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(private http: HttpClient) {
    }

    getAllPosts(): Observable<Post[]> {
        return this.http.get<Post[]>("/api/posts");
    }

    getPostImage(postId: number, picture: number): Observable<Blob> {
        return this.http.get(`/api/posts/image/1/0`, {responseType: "blob"})
    }
}
