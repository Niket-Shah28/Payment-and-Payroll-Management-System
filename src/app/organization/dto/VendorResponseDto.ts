export class VendorResponseDto {
  vendorId!: number;
  name!: string;
  email!: string;
  phoneNumber!: string;
  gstin!: string;
  pan!: string;

  constructor(init?: Partial<VendorResponseDto>) {
    Object.assign(this, init);
  }
}
