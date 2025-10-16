import { BankAccountDetailsDto } from "./bank-account-details-dto";
export interface EmployeeBankInfoDto{
    employeeBankInfoId:number;
    employeeId:number;
    pfNumber:string;
    uanNumber:string;
    accountDetails:BankAccountDetailsDto;
    isActive:boolean;
    createdAt:Date;
    updatedAt:Date;
}