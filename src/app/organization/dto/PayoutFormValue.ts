import { Month } from "./Month";
import { PaymentMode } from "./PaymentMode";
import { PaymentRecipientType } from "./PaymentRecipientType";

export class PayoutFormValue {
  paymentRecipientType: PaymentRecipientType | null = null;
  singlePayment: boolean = true;

  // Single Payment Fields
  amount: number | null = null;
  paymentMode: PaymentMode | null = null;
  scheduledTime: Date | null = null;
  recipientAccountNumber: string | null = null;
  recipientBankName: string | null = null;
  recipientIfscCode: string | null = null;
  recipientAccountHolderName: string | null = null;

  // Bulk Payment Fields
  paymentFileUrl: string | null = null;
  month: Month | null = null;
  year: number | null = null;
}
