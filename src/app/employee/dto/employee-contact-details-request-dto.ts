export interface EmployeeContactDetailsRequestDto {
  email?: string;
  officeEmail?: string;
  personalPhone?: string; // Note: using 'personalPhone' for request as per your service
  emergencyContact?: string;
  emergencyContactRelation?: string;
  alternateMobileNumber?: string;
}