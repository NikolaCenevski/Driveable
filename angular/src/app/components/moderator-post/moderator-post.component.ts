import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PostService} from "../../services/post.service";
import {DomSanitizer} from "@angular/platform-browser";
import {MessageService} from "../../services/message.service";
import {MatDialog} from "@angular/material/dialog";
import {mergeMap} from "rxjs";
import {ReportDialogComponent} from "../post/report-dialog/report-dialog.component";
import {ReportPost} from "../../models/reportPost";
import {ModeratorService} from "../../services/moderator.service";

@Component({
    selector: 'app-moderator-post',
    templateUrl: './moderator-post.component.html',
    styleUrls: ['./moderator-post.component.css']
})
export class ModeratorPostComponent implements OnInit {

    images: any[] = [{
        image: 'https://www.blackgirldangerous.com/wp-content/uploads/2017/01/aTeK8Ka7c.jpeg',
        thumbImage: 'https://www.blackgirldangerous.com/wp-content/uploads/2017/01/aTeK8Ka7c.jpeg',
        title: ''
    }]
    autoSlide: boolean | { interval: number, stopOnHover: boolean } = false;
    showArrow = false;

    post!: ReportPost;

    constructor(
        private route: ActivatedRoute,
        private moderatorService: ModeratorService,
        private postService: PostService,
        private sanitizer: DomSanitizer,
        private messageService: MessageService,
        private router: Router) {
    }

    ngOnInit(): void {
        this.route.paramMap.pipe(
            mergeMap((params) => {
                let id = +params.get('id')!;
                return this.moderatorService.getPost(id);
            })
        ).subscribe({
            next: (data) => {
                this.post = data;
                this.getAllImages(this.post.postId)
            },
            error: err => {
                this.messageService.showErrorMessage(err.error.message)
            }
        })
    }

    getAllImages(postId: number) {
        if (this.post.numOfImages > 1) {
            this.showArrow = true;
            this.autoSlide = {interval: 3, stopOnHover: true}
        }

        for (let i = 0; i < this.post.numOfImages; i++) {
            this.postService.getPostImage(postId, i).subscribe({
                next: (data) => {
                    data.text().then((res) => {
                        let image = res
                        this.images.push({image: image, thumbImage: image, title: ''});
                    });
                }
            })
        }
    }

    approve(postId: number) {
        this.moderatorService.approve(postId).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
                this.router.navigate(['moderator', 'posts'])
            },
            error: err => {
                this.messageService.showErrorMessage(err.error.message)
            }
        })
    }

    delete(postId: number) {
        this.moderatorService.delete(postId).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
                this.router.navigate(['moderator', 'posts'])
            },
            error: err => {
                this.messageService.showErrorMessage(err.error.message)
            }
        })
    }
}
