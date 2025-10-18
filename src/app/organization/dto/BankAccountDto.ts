import { AccountType } from "./AccountType";

export class BankAccountDto {
    constructor(
        public accountId: number,
        public accountNumber: string,
        public accountHolderName: string,
        public ifscCode: string,
        public accountType: AccountType,
        public balance: number
    ) {}
}