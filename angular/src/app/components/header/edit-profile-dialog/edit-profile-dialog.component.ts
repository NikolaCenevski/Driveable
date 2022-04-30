import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {MessageService} from "../../../services/message.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
    selector: 'app-edit-profile-dialog',
    templateUrl: './edit-profile-dialog.component.html',
    styleUrls: ['./edit-profile-dialog.component.css']
})
export class EditProfileDialogComponent implements OnInit {

    numberForm = new FormControl();
    emailForm = new FormControl();
    passwordForm = new FormControl();

    constructor(
        private userService: UserService,
        private messageService: MessageService,
        private dialogRef: MatDialogRef<EditProfileDialogComponent>
    ) {
    }

    ngOnInit(): void {
    }

    changeNumber() {
        this.userService.changeNumber(this.numberForm.value).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
            }
        })
    }

    changeEmail() {
        this.userService.changeEmail(this.emailForm.value).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
            }
        })
    }

    changePassword() {
        this.userService.changePassword(this.passwordForm.value).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
            }
        })
    }
}
