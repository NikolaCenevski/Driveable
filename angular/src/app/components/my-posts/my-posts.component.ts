import {Component, OnInit} from '@angular/core';
import {Post} from "../../models/post";
import {PostService} from "../../services/post.service";
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../../services/user.service";
import {MatDialog} from "@angular/material/dialog";
import {EditPriceDialogComponent} from "./edit-price-dialog/edit-price-dialog.component";
import {mergeMap, of} from "rxjs";

@Component({
    selector: 'app-my-posts',
    templateUrl: './my-posts.component.html',
    styleUrls: ['./my-posts.component.css']
})
export class MyPostsComponent implements OnInit {

    posts: Post[] = [];
    images = new Map();

    constructor(
        private postService: PostService,
        private userService: UserService,
        private sanitizer: DomSanitizer,
        private dialog: MatDialog,
    ) {
    }

    ngOnInit(): void {
        this.userService.getMyPosts().subscribe({
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

    openEditPriceDialog(postId: number) {
        let dialogRef = this.dialog.open(EditPriceDialogComponent, {
            width: '500px',
            data: postId
        })

        dialogRef.afterClosed().pipe(
            mergeMap((data) => {
                if (data == 'success') {
                    return this.userService.getMyPosts();
                }

                return of(this.posts)
            })
        ).subscribe({
            next: (data) => {
                this.posts = data;
                this.getTitleImages();
            }
        })
    }
}
