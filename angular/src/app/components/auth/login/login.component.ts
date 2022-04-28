import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {TokenStorageService} from "../../../services/token-storage.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {finalize} from "rxjs";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm!: FormGroup;
    loading = false;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private tokenStorage: TokenStorageService
    ) {
        if (this.tokenStorage.getToken()) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            username: '',
            password: '',
        });
    }

    onSubmit(): void {
        if (this.loginForm.invalid) {
            return;
        }

        let username = this.loginForm.controls['username'].value;
        let password = this.loginForm.controls['password'].value

        this.loading = true;
        this.authenticationService
            .login(username, password)
            .pipe(
                finalize(() => {
                    this.loading = false;
                })
            ).subscribe({
                next: (data) => {
                    this.tokenStorage.saveToken(data.token);
                    this.tokenStorage.saveUser(JSON.stringify(data));
                    this.router.navigate(['/']);
                },
                error: (error) => {

                }
            }
        );
    }
}