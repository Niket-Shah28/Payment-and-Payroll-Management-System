export interface EmployeeAddressRequestDto {
  currentAddress?: string;
  permanentAddress?: string;
  state?: string;
  city?: string;
  pincode?: string;
  country?: string;
}