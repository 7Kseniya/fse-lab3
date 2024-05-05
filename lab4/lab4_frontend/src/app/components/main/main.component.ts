import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ElementService } from '../../service/element.service';
import { MyElement } from '../../interface/my-element';
import { OneElement } from '../../utils/one-element'
import { HttpClientModule } from '@angular/common/http';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule, ReactiveFormsModule, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit, AfterViewInit, OnDestroy {

  pointsVisible:boolean = false;
  elementList: MyElement[] = [];
  oneElement: OneElement = new OneElement();
  isError: boolean = false;
  error: string = "Invalid values"
  myForm: FormGroup = new FormGroup({});
  @ViewChild("svg") svg!: ElementRef<SVGAElement>;


  constructor(private elementService: ElementService, private fb: FormBuilder, private router: Router, private renderer: Renderer2){
    this.myForm = fb.group({
      'x': ['', [Validators.min(-5), Validators.max(5), Validators.required]],
      'y': ['', [Validators.min(-3), Validators.max(5), Validators.required]],
      'r': ['', [Validators.min(1), Validators.max(4), Validators.required, Validators.pattern("^[0-9]*$")]]
    })
  }

  ngOnInit(): void {
    if(!localStorage.getItem("jwt")){
      this.router.navigate(["/"]);
    }
    this.load();
    this.oneElement.r = 1;

  }
  ngAfterViewInit(): void {
    this.svgClickEvent();
  }
  ngOnDestroy(): void {

  }


  onChange(event: any): void {
    if(this.pointsVisible){
      this.loadPointsToSvg();
    }
  }

  submitForm(): void{

    if(this.myForm.controls['r'].hasError('max') || this.myForm.controls['r'].hasError('min') ||
    this.myForm.controls['y'].hasError('max') || this.myForm.controls['y'].hasError('min') || 
    this.myForm.controls['x'].hasError('max') || this.myForm.controls['x'].hasError('min') ||
    this.myForm.controls['r'].hasError('pattern')){
      this.isError = true;
      return;
    }
    console.log(this.oneElement.x);
    console.log(this.oneElement.y);
    console.log(this.oneElement.r);
    this.isError = false;
    this.elementService.сheck_area(this.oneElement).subscribe(
      (response)=>{
        console.log(response)
        this.load();
      }
    )
  }

  private load(){
    this.elementService.elements().subscribe(
      (response) => {
        console.log(response);
        this.elementList = response.data?.elements || [];
        console.log(this.elementList)
      }
    );
  }

  clear(){
    this.elementService.clear().subscribe();
    this.elementList = [];
    this.clearSvg();
  }

  private check(el: MyElement): boolean {
    return (el.x <= 0 && el.x >= -el.r && el.y >= 0 && el.y <= el.r) || // square
        (el.r >= 0 && el.y <= 0 && Math.sqrt(el.x * el.x + el.y * el.y) <= el.r / 2) || // circle
        (el.x >= -el.r && el.x <= 0 && el.y >= -el.r/2.0 && el.y <= 0 && el.y >= (-el.x/2.0 - el.r/2.0)); // triangle
  }

  private svgClickEvent(): void {
    this.renderer.listen(this.svg.nativeElement, 'click', (event: MouseEvent) => {
      const r : any = this.oneElement.r;
      if(r == 1 || r == 2 || r == 3 || r == 4){

        let x : any  = event.offsetX;
        let y: any  = event.offsetY;
        
        const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
  
        point.setAttribute("cx", x);
        point.setAttribute("cy", y);
        point.setAttribute("r", "3");
        point.setAttribute('class', 'svg-point');

        let gX: any = ((x - 300/2)/(100/r)).toFixed(2)
        let gY: any = ((300/2 - y) / (100/r)).toFixed(2)

        this.oneElement.x = gX;
        this.oneElement.y = gY;

        if(this.check(this.oneElement)){
          point.setAttribute("fill", "green");
        } else {
          point.setAttribute("fill", "red");
        }
        this.svg.nativeElement.append(point);
      }
      this.elementService.сheck_area(this.oneElement).subscribe(
        (response)=>{
          console.log(response)
          this.load();
        }
      )
    });
  }

  clearSvg(): void{
    this.pointsVisible = false;
    const points = this.svg.nativeElement.querySelectorAll('.svg-point');
    points.forEach(point => {
      this.renderer.removeChild(this.svg.nativeElement, point);
    });
  }


  loadPointsToSvg(): void {
    
    this.clearSvg();
    this.pointsVisible = true;
    this.elementList.forEach(el => {
      let r: any = this.oneElement.r;
      if(r == 1 || r == 2 || r == 3 || r == 4){
        const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        
        point.setAttribute("r", "3");
        point.setAttribute('class', 'svg-point');
        
        let cx: any =  (el.x * (100 / r) + 300 / 2);
        let cy: any =  (300/2 - (el.y * (100 / r)));

        point.setAttribute("cx", cx);
        point.setAttribute("cy", cy);

        this.oneElement.x = el.x;
        this.oneElement.y = el.y;

        if(this.check(this.oneElement)){
          point.setAttribute("fill", "green");
        } else {
          point.setAttribute("fill", "red");
        }
        this.svg.nativeElement.append(point);
      }
    });
    };

    logout(): void{
      localStorage.clear();
      this.router.navigate(["/"])
    }

}
