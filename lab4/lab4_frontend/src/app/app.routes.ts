import { Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { MainComponent } from './components/main/main.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

export const routes: Routes = [
    { path: '', component: WelcomeComponent },
    { path: 'main', component: MainComponent },
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent}
];
