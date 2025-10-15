import { Component } from '@angular/core';

@Component({
  selector: 'app-home-component',
  standalone: false,
  templateUrl: './home-component.html',
  styleUrl: './home-component.css'
})
export class HomeComponent {

   companyName = 'Aurionpro Solutions';
  tagline = 'Building the Future, One Solution at a Time.';
  mission = 'Our core mission is to empower innovation through technology, fostering a collaborative and growth-oriented environment for all our dedicated employees. Your role is vital to this success.';

}
