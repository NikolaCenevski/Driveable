import {Component, OnInit} from '@angular/core';
import {MessageService} from "./services/message.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    constructor(private messageService: MessageService, private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {

        this.messageService.$successMessages.subscribe({
            next: message => {
                this.openMessage(message, 'success')
            }
        });

        this.messageService.$errorMessages.subscribe({
            next: message => {
                this.openMessage(message, 'error')
            }
        });
    }

    openMessage(message: string, type: string) {
        this.snackBar.open(message, 'X', {
            horizontalPosition: "center",
            verticalPosition: "bottom",
            duration: 3000,
            panelClass: [`${type}Alert`]
        });
    }

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

    // onFileChange(event: any) {
    //
    //     this.files = []
    //     if (event.target.files && event.target.files.length) {
    //         for (let file of event.target.files) {
    //             const reader = new FileReader();
    //             reader.readAsDataURL(file);
    //             reader.onload = () => {
    //                 this.files.push(reader.result)
    //             };
    //         }
    //     }
    // }
//
//     getAllImages(imgNum: any) {
//         for (let i = 0; i < this.post.imgNum; i++) {
//             this.http.get(`/api/posts/1/image/${i}`, {responseType: "blob"})
//                 .subscribe({
//                     next: data => {
//                         data.text().then((res) => {
//                             this.images.push(this.sanitizer.bypassSecurityTrustUrl(res));
//                         });
//                     }
//                 })
//         }
//     }
//
//     this.http.get("/api/posts/1").subscribe({
//                                                 next: (data: any) => {
//     this.post = data;
//     this.getAllImages(this.post.imgNum);
// }
// })
//
//     submit() {
//         console.log(this.files);
//         this.http.post('api/posts/upload', {
//             id: this.id.value,
//             files: this.files
//         }).subscribe({
//             next: data => {
//
//             }
//         })
//     }
}
