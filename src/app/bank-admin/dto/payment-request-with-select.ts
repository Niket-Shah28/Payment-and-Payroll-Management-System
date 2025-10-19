import { PaymentRequest } from "./payment-request-dto";

interface PaymentRequestWithSelect extends PaymentRequest {
  selected?: boolean;
}
