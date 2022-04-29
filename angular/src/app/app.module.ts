import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {JwtInterceptor} from './helpers/jwt.interceptor';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HeaderComponent} from './components/header/header.component';
import {AuthComponent} from './components/auth/auth.component';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule} from "@angular/router";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatInputModule} from "@angular/material/input";
import {AppRoutingModule} from "./app-routing.module";
import {MatChipsModule} from "@angular/material/chips";
import { PostsComponent } from './components/posts/posts.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { CreatePostDialogComponent } from './components/header/create-post-dialog/create-post-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatCheckboxModule} from "@angular/material/checkbox";
import { PostComponent } from './components/post/post.component';
import {NgImageSliderModule} from "ng-image-slider";
import { ReportDialogComponent } from './components/post/report-dialog/report-dialog.component';

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        AuthComponent,
        LoginComponent,
        RegisterComponent,
        PostsComponent,
        CreatePostDialogComponent,
        PostComponent,
        ReportDialogComponent,
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        MatMenuModule,
        MatIconModule,
        MatToolbarModule,
        FlexLayoutModule,
        MatButtonModule,
        RouterModule,
        MatCardModule,
        MatFormFieldModule,
        MatProgressSpinnerModule,
        MatInputModule,
        MatChipsModule,
        MatSidenavModule,
        MatSnackBarModule,
        MatDialogModule,
        MatCheckboxModule,
        NgImageSliderModule,
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
