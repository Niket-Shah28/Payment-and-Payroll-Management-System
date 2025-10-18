export class VendorRequestDto {
  name!: string;
  cinNumber?: string;
  email?: string;
  phoneNumber?: string;
  address!: string;
  gstin?: string;
  pan?: string;
  tan?: string;
  contractTitle!: string;
  startDate!: string;  // ISO format for LocalDate
  endDate!: string;
  contractDocumentUrl?: string;

  constructor(init?: Partial<VendorRequestDto>) {
    Object.assign(this, init);
  }
}
