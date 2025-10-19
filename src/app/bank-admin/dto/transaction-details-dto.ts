export interface TransactionDetailsDto {
  transactionId: string;
  amount: number;
  createdAt: string;
  description: string;
  destinationAccountNumber: string;
  paymentMode: string;      // IMPS, NEFT, RTGS
  paymentType: string;      // CREDIT, DEBIT
  receiverBankName: string;
  receiverHolderName: string;
  receiverIfscCode: string;
  reference_number: string;
  sourceAccountNumber: string;
  transactionStatus: string; // PASS, FAIL
}
