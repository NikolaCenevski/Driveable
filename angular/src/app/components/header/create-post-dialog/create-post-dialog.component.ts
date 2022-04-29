import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl} from "@angular/forms";
import {PostService} from "../../../services/post.service";
import {MessageService} from "../../../services/message.service";
import {MatDialogRef} from "@angular/material/dialog";
import {catchError, mergeMap, Observable, of, startWith} from "rxjs";

@Component({
    selector: 'app-create-post-dialog',
    templateUrl: './create-post-dialog.component.html',
    styleUrls: ['./create-post-dialog.component.css']
})
export class CreatePostDialogComponent implements OnInit {

    createForm = this.formBuilder.group({
        title: '',
        description: '',
        horsepower: 0,
        mileage: 0,
        manufacturingYear: '',
        price: 0,
        carType: new FormControl([]),
        color: '',
        images: '',
        model: '',
        manufacturer: '',
        isNew: false,
    })
    files: any[] = []
    carTypes: string[] = []

    manufacturers: string[] = []
    filteredManufacturers: string[] = [];

    models: string[] = []
    filteredModels: string[] = [];

    constructor(
        private formBuilder: FormBuilder,
        private messageService: MessageService,
        private postService: PostService,
        private dialogRef: MatDialogRef<CreatePostDialogComponent>
    ) {
    }

    ngOnInit(): void {

        this.postService.getManufacturers().subscribe({
            next: (data) => {
                this.manufacturers = data
                this.filteredManufacturers = this._filterManufacturers('')
            }
        })

        this.postService.getCarTypes().subscribe({
            next: (data) => {
                this.carTypes = data
            }
        })

        this.createForm.controls['manufacturer'].valueChanges.pipe(
            startWith(''),
            mergeMap(value => {
                this.filteredManufacturers = this._filterManufacturers(value)
                return this.postService.getModels(value)
            }),
            catchError((): Observable<string[]> => of([]))
        ).subscribe({
            next: (data) => {
                this.models = data
                this.filteredModels = this._filterModels('')
            }
        });

        this.createForm.controls['model'].valueChanges.pipe(
            startWith(''),
        ).subscribe({
            next: (data) => {
                this.filteredModels = this._filterModels(data)
            }
        })
    }

    post() {
        let value = {
            ...this.createForm.value,
            images: [...this.files]
        }

        this.postService.createPost(value).subscribe({
            next: data => {
                this.messageService.showSuccessMessage(data.message)
                this.dialogRef.close()
                this.postService.newPost.next('')
            }
        })
    }

    onFileChange(event: any) {
        this.files = []
        if (event.target.files && event.target.files.length) {
            for (let file of event.target.files) {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => {
                    this.files.push(reader.result)
                };
            }
        }
    }

    onNoClick() {
        this.dialogRef.close()
    }

    private _filterManufacturers(value: string): string[] {
        const filterValue = value.toLowerCase();

        return this.manufacturers.filter(option => option.toLowerCase().includes(filterValue));
    }

    private _filterModels(value: string): string[] {
        const filterValue = value.toLowerCase();

        return this.models.filter(option => option.toLowerCase().includes(filterValue));
    }
}
