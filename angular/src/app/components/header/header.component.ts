import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {CreatePostDialogComponent} from "./create-post-dialog/create-post-dialog.component";
import {EditProfileDialogComponent} from "./edit-profile-dialog/edit-profile-dialog.component";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    constructor(
        public tokenStorageService: TokenStorageService,
        private router: Router,
        private dialog: MatDialog
        ) {
    }

    ngOnInit(): void {
    }

    name() {
        let user = this.tokenStorageService.getUser();
        if(user) {
            return user.name + " " + user.surname
        }

        return ''
    }

    signOut() {
        this.tokenStorageService.signOut();
        this.router.navigate(['/login']);
    }

    isLoggedIn() {
        return !!this.tokenStorageService.getUser();
    }

    openCreatPostDialog() {
        this.dialog.open(CreatePostDialogComponent, {
            width: '800px'
        })
    }

    openEditProfileDialog() {
        this.dialog.open(EditProfileDialogComponent, {
            width: '500px'
        })
    }
}
