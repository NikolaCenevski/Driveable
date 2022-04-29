import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of, Subject} from "rxjs";
import {Post} from "../models/post";
import {Message} from "../models/message";

@Injectable({
    providedIn: 'root'
})
export class PostService {

    newPost = new Subject();

    constructor(private http: HttpClient) {
    }

    getAllPosts(): Observable<Post[]> {
        return this.http.get<Post[]>("/api/posts");
    }

    getPosts(
        isNew: boolean,
        manufacturer: string,
        model: string,
        yearFrom: string,
        yearTo: string,
        priceFrom: string,
        priceTo: string,
        color: string,
        sortBy: string,
        carTypes: string[]) {
        return this.http.post<Post[]>("/api/posts", {
            isNew: isNew,
            manufacturer: manufacturer == '' ? null : manufacturer,
            model: model == '' ? null : model,
            yearFrom: yearFrom == '' ? null : yearFrom,
            yearTo: yearTo == '' ? null : yearTo,
            priceFrom: priceFrom == '' ? null : priceFrom,
            priceTo: priceTo == '' ? null : priceTo,
            color: color == '' ? null : color,
            sortBy: sortBy == '' ? null : sortBy,
            carTypes: carTypes.length == 0 ? null : carTypes
        })
    }

    getPost(id: number): Observable<Post> {
        return this.http.get<Post>(`/api/posts/${id}`);
    }

    getPostImage(postId: number, picture: number): Observable<Blob> {
        return this.http.get(`/api/posts/image/${postId}/${picture}`, {responseType: "blob"})
    }

    createPost(value: any): Observable<Message> {
        return this.http.post<Message>('/api/posts/create', {...value});
    }

    reportPost(postId: number, description: string): Observable<Message> {
        return this.http.post<Message>(`/api/posts/${postId}/report`, description);
    }

    getManufacturers(): Observable<string[]> {
        return this.http.get<string[]>('/api/posts/manufacturer')
    }

    getModels(manufacturer: string): Observable<string[]> {
        return this.http.get<string[]>(`/api/posts/model?manufacturer=${manufacturer}`)
    }

    getCarTypes(): Observable<string[]> {
        return this.http.get<string[]>('/api/posts/carTypes');
        // return of(['asd', 'dasd'])
    }
}
