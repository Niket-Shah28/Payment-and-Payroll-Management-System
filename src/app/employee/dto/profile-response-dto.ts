import {EmployeeSalary} from './employee-salary'
import {Gender} from './gender'
import {Salutation} from './salutation'

export interface ProfileResponseDto {
  firstName: string;
  middleName: string;
  lastName: string;
  profilePhotoUrl: string;
  gender: Gender;
  salutation: Salutation;
  spouse: string;
  dateOfBirth: string; // Use string for Date objects from backend
  bloodGroup: string;
  nationality: string;
  panNumber: string;
  aadharNumber: string;
  salary: EmployeeSalary;
}