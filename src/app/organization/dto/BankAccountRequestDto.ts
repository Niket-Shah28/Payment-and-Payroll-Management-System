import { AccountType } from "./AccountType";

export class BankAccountRequestDto {
    constructor(
        public accountNumber: string,
        public accountHolderName: string,
        public ifscCode: string,
        public accountType: AccountType,
        public balance: number
    ) {}
}