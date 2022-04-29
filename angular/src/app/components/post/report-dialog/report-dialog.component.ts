import {Component, Inject} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MessageService} from "../../../services/message.service";
import {PostService} from "../../../services/post.service";

@Component({
    selector: 'app-report-dialog',
    templateUrl: './report-dialog.component.html',
    styleUrls: ['./report-dialog.component.css']
})
export class ReportDialogComponent {

    descriptionFormControl = new FormControl('');

    constructor(
        private dialogRef: MatDialogRef<ReportDialogComponent>,
        private messageService: MessageService,
        private postService: PostService,
        @Inject(MAT_DIALOG_DATA) public postId: number,
        ) {
    }

    report() {
        this.postService.reportPost(this.postId, this.descriptionFormControl.value).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
                this.dialogRef.close()
            },
            error: err => {
                this.messageService.showErrorMessage(err.error.message)
            }
        })
    }

    onNoClick() {
        this.dialogRef.close()
    }
}
