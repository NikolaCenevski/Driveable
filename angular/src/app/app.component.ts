import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {DomSanitizer} from '@angular/platform-browser';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    title = 'Driveable';
    imageInput = new FormControl()

    post: any;
    images: any[] = [];

    getAllImages(imgNum: any) {
        for(let i=0; i<this.post.imgNum; i++) {
        this.http.get(`/api/posts/1/image/${i}`, {responseType: "blob"})
            .subscribe({
                next: data => {
                    data.text().then((res) => {
                        this.images.push(this.sanitizer.bypassSecurityTrustUrl(res));
                    });
                }
            })
        }
    }

    ngOnInit(): void {
        this.http.get("/api/posts/1").subscribe({
            next: (data: any) => {
                this.post = data;
                this.getAllImages(this.post.imgNum);
            }
        })


        //   this.http.post("/api/auth/register",{
        //     username:"nikola123",
        //     password:"nikola123",
        //     email:"nklnkl@gmail.com",
        //     phoneNumber:"07889898",
        //     name:"NikolaNAME",
        //     surname:"SurnameNikola"
        //
        //   }).subscribe({
        //     next:()=>{
        //       this.http.post("/api/auth/authenticate",{
        //       userName:"nikola123",
        //       password:"nikola123"
        //     }).subscribe({
        //       next:data=>{console.log(data)}
        //     })}
        //   })
    }

    id = new FormControl()
    files: any[] = []


    constructor(private http: HttpClient, private sanitizer: DomSanitizer) {
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


    submit() {
        console.log(this.files);
        this.http.post('api/posts/upload', {
            id: this.id.value,
            files: this.files
        }).subscribe({
            next: data => {

            }
        })
    }
}
