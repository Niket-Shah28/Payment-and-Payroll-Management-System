import { Component } from '@angular/core';

@Component({
  selector: 'app-home-component',
  standalone: false,
  templateUrl: './home-component.html',
  styleUrls: ['./home-component.css']
})
export class HomeComponent {

  organizationName: string = "AurionPro Solutions";

  currentDate: Date = new Date();
  currentTime: string = '';

  ngOnInit(): void {
    this.updateDateTime();
  }

  updateDateTime(): void {
    // Update time every second
    setInterval(() => {
      this.currentDate = new Date();
      this.currentTime = this.currentDate.toLocaleTimeString();
    }, 1000);
  }

}
