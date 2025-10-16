import { Component } from '@angular/core';
import { AccountService } from '../../services/account-service';
import { EmployeeBankInfoDto } from '../../dto/employee-bank-info-dto';
import { EmployeeBankInfoUpdateDto } from '../../dto/employee-bank-info-update-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-bank-info-list',
  standalone: false,
  templateUrl: './employee-bank-info-list.html',
  styleUrl: './employee-bank-info-list.css'
})
export class EmployeeBankInfoList {

  bankInfos: EmployeeBankInfoDto[] = [];

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.loadBankInfos();
  }

  loadBankInfos() {
    this.accountService.getAllBankInfo().subscribe(info => this.bankInfos = info);
  }

  editBankInfo(bankInfo: EmployeeBankInfoDto) {
    const newUAN = prompt('Edit UAN number:', bankInfo.uanNumber);
    if (!newUAN) return;

    const updateDto: EmployeeBankInfoUpdateDto = {
      uanNumber: newUAN,
      pfNumber: bankInfo.pfNumber,
      isActive: bankInfo.isActive
    };
  }


 viewBankInfo(bankInfoId: number) {
    this.router.navigate(['/employee/dashboard/employee-bank-info-list', bankInfoId]);
  }

}
