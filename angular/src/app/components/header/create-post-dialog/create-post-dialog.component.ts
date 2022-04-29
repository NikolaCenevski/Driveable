import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {PostService} from "../../../services/post.service";
import {MessageService} from "../../../services/message.service";
import {MatDialogRef} from "@angular/material/dialog";

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
        carType: '',
        color: '',
        images: '',
        model: '',
        manufacturer: '',
        isNew: false,
    })

    files: any[] = []

    constructor(
        private formBuilder: FormBuilder,
        private messageService: MessageService,
        private postService: PostService,
        private dialogRef: MatDialogRef<CreatePostDialogComponent>
    ) {
    }

    ngOnInit(): void {
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
}
