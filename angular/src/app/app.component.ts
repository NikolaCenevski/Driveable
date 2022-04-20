import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Driveable';
  ngOnInit(): void {

      this.http.post("/api/auth/register",{
        username:"nikola123",
        password:"nikola123",
        email:"nklnkl@gmail.com",
        phoneNumber:"07889898",
        name:"NikolaNAME",
        surname:"SurnameNikola"

      }).subscribe({
        next:()=>{      
          this.http.post("/api/auth/authenticate",{
          userName:"nikola123",
          password:"nikola123"
        }).subscribe({
          next:data=>{console.log(data)}
        })}
      })
    }
  
  constructor(private http:HttpClient){
  
  }
}
