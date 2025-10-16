import { Timestamp } from "rxjs";
import { AccountType } from "./account-type";
export interface BankAccountDetailsDto {
  accountId:number;
    accountNumber: string;

    bankName: string;
    ifscCode: string;
    accountType: AccountType;
    accountHolderName: string;
    isActive: boolean;
    createdAt: Date;
    updatedAt: Date;

}