import { AccountType } from "./account-type";
export interface BankAccountDetailsUpdateDto {

    accountNumber: string;

    bankName: string;
    ifscCode: string;
    accountType: AccountType;
    accountHolderName: string;
    isActive: boolean;

}