import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TicketService } from '../../../services/ticket-service'; 
import { ProfileResponseDto } from '../../../dto/profile-response-dto';
import { TicketCreateDto } from '../../../dto/ticket-create-dto'; 
import { EmployeeService } from '../../../services/employee-service';

@Component({
  selector: 'app-raise-ticket',
  standalone: false,
  templateUrl: './raise-ticket.html',
  styleUrl: './raise-ticket.css'
})
export class RaiseTicket {
  ticketForm!: FormGroup;
  profile!: ProfileResponseDto;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private employeeService: EmployeeService
  ) {}

  ngOnInit() {
    this.ticketForm = this.fb.group({
      query: ['', [Validators.required, Validators.minLength(10)]]
    });

    // Fetch employee profile for employeeId & organizationId
    this.employeeService.getProfile().subscribe(profile => this.profile = profile);
  }

  submitTicket() {
    if (!this.profile) return alert('Profile not loaded yet.');

    const createDto: TicketCreateDto = {
      query: this.ticketForm.value.query
    };

    this.ticketService.createTicket(createDto).subscribe({
      next: (ticket) => {
        alert('Ticket submitted successfully!');
        this.ticketForm.reset();
      },
      error: (err) => console.error(err)
    });
  }

}
