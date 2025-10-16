import { Component } from '@angular/core';

@Component({
  selector: 'app-home-component',
  standalone: false,
  templateUrl: './home-component.html',
  styleUrls: ['./home-component.css']
})
export class HomeComponent {

  organizationName: string = "AurionPro Solutions";

}
