import { Gender } from "./gender";
import { EmployeeSalary } from "./employee-salary";
import { Salutation } from "./salutation";

export interface ProfileRequestDto {
  firstName?: string;
  middleName?: string;
  lastName?: string;
  profilePhotoUrl?: string;
  gender?: Gender;
  salutation?: Salutation;
  spouse?: string;
  dateOfBirth?: string;
  bloodGroup?: string;
  nationality?: string;
  panNumber?: string;
  aadharNumber?: string;
  salary?: EmployeeSalary;
}