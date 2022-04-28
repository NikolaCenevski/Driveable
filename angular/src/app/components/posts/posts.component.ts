import {Component, OnInit} from '@angular/core';
import {PostService} from "../../services/post.service";
import {Post} from "../../models/post";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'app-posts',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

    posts: Post[] = [];
    images: any[] = []

    constructor(private postService: PostService, private sanitizer: DomSanitizer) {
    }

    ngOnInit(): void {
        this.postService.getAllPosts().subscribe({
            next: (data) => {
                this.posts = data
                this.getTitleImages();
            }
        })
    }

    getTitleImages() {
        for (let post of this.posts) {
            this.postService.getPostImage(post.id, 0).subscribe({
                next: (data) => {
                    data.text().then((res) => {
                        this.images.push(this.sanitizer.bypassSecurityTrustUrl("data:image/png;base64, " + res));
                    });
                }
            })
        }
    }
}
