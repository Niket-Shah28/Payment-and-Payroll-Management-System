import { PaymentRecipientType } from "./payment-receipient-type";
import { Status } from "./status";
import { PaymentMode } from "./payment-mode";

export interface PaymentRequest {
  paymentRequestId: number;
  amount: number;
  paymentFileUrl: string;
  paymentRecipientType: PaymentRecipientType;
  recipientAccountNumber: string;
  recipientBankName: string;
  recipientIfscCode: string;
  recipientName: string;
  organizationName: string;
  scheduledTime: string; 
  singlePayment: boolean;
  status: Status;
  month: string;
  year: number;
  paymentMode: PaymentMode;
}