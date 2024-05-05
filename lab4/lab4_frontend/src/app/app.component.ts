import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./components/header/header.component";



@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    imports:[CommonModule ,RouterOutlet, HeaderComponent],
    standalone:true
})
export class AppComponent {
  title = 'lab4_frontend';
}
