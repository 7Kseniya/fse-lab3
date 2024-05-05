import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { User } from '../../utils/user';
import { MyUser } from '../../interface/my-user';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  user: MyUser = new User();
  error: string = "Username is taken"

  isError: boolean = false;

  constructor(private userService: UserService , private router: Router){}


  ngOnInit(): void {
  }

  sendRegister(){
    console.log(this.user)
    if(this.user.login.length > 6 || this.user.password.length > 8){
      this.userService.registerUser(this.user).subscribe(
        (response) => {
          if(response.status != "CONFLICT"){
            localStorage.setItem('jwt', response.jwt);
            console.log(response);
            this.router.navigate(['/main'])
          } else {
            this.error = "Username is taken";
            this.isError = true;
          }
        }
      );
    } else {
      this.error = "Username must be at least 6 characters long\n  Password must be at least 8 characters long";
      this.isError = true;
    }
  }
}
