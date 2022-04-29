import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthComponent} from "./components/auth/auth.component";
import {LoginComponent} from "./components/auth/login/login.component";
import {RegisterComponent} from "./components/auth/register/register.component";
import {PostsComponent} from "./components/posts/posts.component";
import {PostComponent} from "./components/post/post.component";

const routes: Routes = [
    {
        path: 'auth',
        component: AuthComponent,
        children:
            [
                {
                    path: 'login',
                    component: LoginComponent,
                },
                {
                    path: 'register',
                    component: RegisterComponent
                }
            ],

    },
    {
        path: 'posts',
        component: PostsComponent
    },
    {
        path: 'post/:id',
        component: PostComponent
    },
    {
        path: '**',
        redirectTo: '/posts'
    }
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
