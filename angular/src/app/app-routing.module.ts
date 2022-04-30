import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthComponent} from "./components/auth/auth.component";
import {LoginComponent} from "./components/auth/login/login.component";
import {RegisterComponent} from "./components/auth/register/register.component";
import {PostsComponent} from "./components/posts/posts.component";
import {PostComponent} from "./components/post/post.component";
import {MyPostsComponent} from "./components/my-posts/my-posts.component";
import {ModeratorPostsComponent} from "./components/moderator-posts/moderator-posts.component";
import {ModeratorPostComponent} from "./components/moderator-post/moderator-post.component";
import {AuthGuard} from "./helpers/auth.guard";

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
        path: 'posts/:page',
        component: PostsComponent
    },
    {
        path: 'post/:id',
        component: PostComponent
    },
    {
        path: 'myPosts',
        component: MyPostsComponent
    },
    {
        path: 'moderator/posts',
        component: ModeratorPostsComponent,
        canActivate: [AuthGuard],
        data: {role: 'MODERATOR'}
    },
    {
        path: 'moderator/posts/:id',
        component: ModeratorPostComponent,
        canActivate: [AuthGuard],
        data: {role: 'MODERATOR'}
    },
    {
        path: '**',
        redirectTo: '/posts/0'
    }
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
