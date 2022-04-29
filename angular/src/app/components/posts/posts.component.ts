import {Component, OnInit} from '@angular/core';
import {PostService} from "../../services/post.service";
import {Post} from "../../models/post";
import {DomSanitizer} from "@angular/platform-browser";
import {FormBuilder, FormControl} from "@angular/forms";
import {mergeMap} from "rxjs";

@Component({
    selector: 'app-posts',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

    posts: Post[] = [];
    images = new Map();
    manufacturers: string[] = []
    models: string[] = [];
    carTypes: string[] = []

    postsForm = this.formBuilder.group({
        isNew: false,
        manufacturer: '',
        model: '',
        yearFrom: '',
        yearTo: '',
        priceFrom: '',
        priceTo: '',
        color: '',
        sortBy: 'date',
        carTypes: new FormControl([])
    })

    constructor(
        private postService: PostService,
        private formBuilder: FormBuilder,
        private sanitizer: DomSanitizer
    ) {
    }

    ngOnInit(): void {
        this.postService.getAllPosts().subscribe({
            next: (data) => {
                this.posts = data
                this.getTitleImages();
            }
        })

        this.postService.getManufacturers().subscribe({
            next: (data) => {
                this.manufacturers = data
            }
        })

        this.postService.getCarTypes().subscribe({
            next: (data) => {
                this.carTypes = data
            }
        })

        this.postsForm.controls['manufacturer'].valueChanges.pipe(
            mergeMap((value) => this.postService.getModels(value))
        ).subscribe({
            next: (data) => {
                this.models = data
            }
        })

        this.postService.newPost.pipe(
            mergeMap(() => this.postService.getAllPosts())
        ).subscribe({
            next: (data) => {
                this.posts = data
                this.getTitleImages();
            }
        })
    }

    search() {
        let isNew = this.postsForm.controls['isNew'].value
        let manufacturer = this.postsForm.controls['manufacturer'].value
        let model = this.postsForm.controls['model'].value
        let yearFrom = this.postsForm.controls['yearFrom'].value
        let yearTo = this.postsForm.controls['yearTo'].value
        let priceFrom = this.postsForm.controls['priceFrom'].value
        let priceTo = this.postsForm.controls['priceTo'].value
        let color = this.postsForm.controls['color'].value
        let sortBy = this.postsForm.controls['sortBy'].value
        let carTypes = this.postsForm.controls['carTypes'].value
        this.postService.getPosts(isNew, manufacturer, model, yearFrom, yearTo, priceFrom, priceTo, color, sortBy, carTypes).subscribe({
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
                        this.images.set(post.id, this.sanitizer.bypassSecurityTrustUrl(res));
                    });
                    console.log(this.images)
                }
            })
        }
    }
}
