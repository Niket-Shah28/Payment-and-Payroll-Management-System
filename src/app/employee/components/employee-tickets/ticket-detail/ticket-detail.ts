import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from '../../../services/ticket-service'; 
import { TicketDto } from '../../../dto/ticket-dto';
import { TicketResponseDto } from '../../../dto/ticket-response-dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeService } from '../../../services/employee-service'; 

@Component({
  selector: 'app-ticket-detail',
  standalone: false,
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css'
})
export class TicketDetail {

 ticket!: TicketDto;
  replyText: string = '';
  profile: any;

  constructor(
    private route: ActivatedRoute,
    private ticketService: TicketService,
    private employeeService: EmployeeService
  ) {}

  ngOnInit() {
    const ticketId = +this.route.snapshot.paramMap.get('ticketId')!;
    //  this.ticketId = Number(this.route.snapshot.paramMap.get('ticketId'));
    this.ticketService.getTicketWithResponses(ticketId).subscribe(t => this.ticket = t);
    this.employeeService.getProfile().subscribe(p => this.profile = p);
  }

  sendReply() {
    if (!this.replyText.trim()) return;
    const dto: TicketResponseDto = {
      response: this.replyText,
      employeeId: this.profile.employeeId,
      organizationId: this.profile.organizationId,
      ticketId: this.ticket.ticketId
    };
    this.ticketService.giveReplyToTicketResponse(dto).subscribe(res => {
      this.ticket.responses.push(res);
      this.replyText = '';
    });
  }

}
