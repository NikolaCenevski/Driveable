import {Component, OnInit} from '@angular/core';
import {finalize} from "rxjs";
import {AuthenticationService} from "../../../services/authentication.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MessageService} from "../../../services/message.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    registerForm!: FormGroup;
    loading = false;

    constructor(
        private formBuilder: FormBuilder,
        private authenticationService: AuthenticationService,
        private messageService: MessageService,
    ) {
    }

    ngOnInit(): void {
        this.registerForm = this.formBuilder.group({
            username: '',
            password: '',
            name: '',
            surname: '',
            email: '',
            phoneNumber: '',
        });
    }


    onSubmit(): void {

        if (this.registerForm.invalid) {
            return;
        }

        let username = this.registerForm.controls['username'].value;
        let password = this.registerForm.controls['password'].value;
        let email = this.registerForm.controls['email'].value;
        let phoneNumber = this.registerForm.controls['phoneNumber'].value;
        let name = this.registerForm.controls['name'].value;
        let surname = this.registerForm.controls['surname'].value;

        this.loading = true;
        this.authenticationService.register(
            username,
            password,
            email,
            phoneNumber,
            name,
            surname).pipe(
            finalize(() => {
                this.loading = false;
            })
        ).subscribe({
            next: (data) => {
                this.messageService.showSuccessMessage(data.message)
            },
            error: err => {
                this.messageService.showErrorMessage(err.error.message)
            }
        });
    }
}
