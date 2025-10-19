export interface TransactionDetailsDto {
  transactionId: string;
  amount: number;
  createdAt: string; // ISO timestamp from backend
  description: string;
  destinationAccountNumber: string;
  paymentMode: string;     // IMPS, NEFT, RTGS
  paymentType: string;     // CREDIT, DEBIT
  receiverBankName?: string;
  receiverHolderName?: string;
  receiverIfscCode?: string;
  reference_number?: string | null;
  sourceAccountNumber?: string;
  transactionStatus?: string; // PASS | FAIL
}
