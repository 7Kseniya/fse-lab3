import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../service/user.service';
import { MyUser } from '../../interface/my-user';
import { User } from '../../utils/user';
import { FormsModule } from '@angular/forms';
import { response } from 'express';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  user: MyUser = new User();
  error: string = "Incorrect username or password"
  isError: boolean = false;

  constructor(private userService: UserService , private router: Router){}


  ngOnInit(): void {
    if(localStorage.getItem("jwt")){
      this.router.navigate(['/main'])
    }
  }

  sendLogin(){
    console.log(this.user)
    this.userService.loginUser(this.user).subscribe(
      (response) => {
        if(response.status != "UNAUTHORIZED"){
          localStorage.setItem('jwt', response.jwt);
          console.log(response);
          this.router.navigate(['/main'])
        } else {
          this.isError = true;
        }
      }
    );
  }

  


}
