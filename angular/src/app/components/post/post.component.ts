import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {mergeMap} from "rxjs";
import {PostService} from "../../services/post.service";
import {Post} from "../../models/post";
import {MessageService} from "../../services/message.service";
import {DomSanitizer} from "@angular/platform-browser";
import {MatDialog} from "@angular/material/dialog";
import {ReportDialogComponent} from "./report-dialog/report-dialog.component";

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

    images: any[] = [{
        image: 'https://www.blackgirldangerous.com/wp-content/uploads/2017/01/aTeK8Ka7c.jpeg',
        thumbImage: 'https://www.blackgirldangerous.com/wp-content/uploads/2017/01/aTeK8Ka7c.jpeg',
        title: ''
    }]
    autoSlide: boolean | {interval: number, stopOnHover: boolean} = false;
    showArrow = false;

    post!: Post;

    constructor(
        private route: ActivatedRoute,
        private postService: PostService,
        private sanitizer: DomSanitizer,
        private messageService: MessageService,
        private dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.route.paramMap.pipe(
            mergeMap((params) => {
                let id = +params.get('id')!;
                return this.postService.getPost(id);
            })
        ).subscribe({
            next: (data) => {
                this.post = data;
                this.getAllImages(this.post.id)
            },
            error: err => {
                this.messageService.showErrorMessage(err.error.message)
            }
        })
    }

    getAllImages(postId: number) {
        if(this.post.numOfImages > 1) {
            this.showArrow = true;
            this.autoSlide = {interval: 3, stopOnHover: true}
        }

        for (let i = 0; i < this.post.numOfImages; i++) {
            this.postService.getPostImage(postId, i).subscribe({
                next: (data) => {
                    data.text().then((res) => {
                        // let image = this.sanitizer.bypassSecurityTrustUrl(res);
                        let image = res
                        this.images.push({image: image, thumbImage: image, title: ''});
                        // this.images.push({image: '', thumbImage: '', title: ''});
                        // this.slide = this.slides.length
                    });
                }
            })
        }
    }

    openReportDialog() {
        this.dialog.open(ReportDialogComponent, {
            width: '500px',
            data: this.post.id
        })
    }
}
