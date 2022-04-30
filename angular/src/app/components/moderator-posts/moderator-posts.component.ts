import {Component, OnInit} from '@angular/core';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ModeratorService} from "../../services/moderator.service";

@Component({
    selector: 'app-moderator-posts',
    templateUrl: './moderator-posts.component.html',
    styleUrls: ['./moderator-posts.component.css']
})
export class ModeratorPostsComponent implements OnInit {

    posts: Post[] = [];
    images = new Map();

    constructor(
        private postService: PostService,
        private moderatorService: ModeratorService,
        private sanitizer: DomSanitizer,
    ) {
    }

    ngOnInit(): void {
        this.moderatorService.getAllReportPosts().subscribe({
            next: (data) => {
                this.posts = data;
                this.getTitleImages();
            }
        })
    }

    getTitleImages() {
        for (let post of this.posts) {
            this.postService.getPostImage(post.id, 0).subscribe({
                next: (data) => {
                    data.text().then((res) => {
                        this.images.set(post.id, this.sanitizer.bypassSecurityTrustUrl(res));
                    });
                    console.log(this.images)
                }
            })
        }
    }
}
