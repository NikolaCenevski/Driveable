import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl} from "@angular/forms";
import {MessageService} from "../../../services/message.service";
import {Post} from "../../../models/post";
import {PostService} from "../../../services/post.service";

@Component({
    selector: 'app-edit-price-dialog',
    templateUrl: './edit-price-dialog.component.html',
    styleUrls: ['./edit-price-dialog.component.css']
})
export class EditPriceDialogComponent implements OnInit {

    price = new FormControl()

    constructor(
        @Inject(MAT_DIALOG_DATA) public postId: number,
        private dialogRef: MatDialogRef<EditPriceDialogComponent>,
        private messageService: MessageService,
        private postService: PostService
    ) {
    }

    ngOnInit(): void {
    }

    submit() {
        this.postService.editPrice(this.postId, this.price.value).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
                this.dialogRef.close('success')
            }
        })
    }

    onNoClick() {
        this.dialogRef.close()
    }

}
