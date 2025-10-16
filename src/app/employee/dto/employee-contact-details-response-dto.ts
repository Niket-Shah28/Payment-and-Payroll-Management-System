export interface EmployeeContactDetailsResponseDto {
  email: string;
  officeEmail: string;
  phoneNumber: string; // Used for personal phone in backend service logic
  emergencyContact: string;
  emergencyContactRelation: string;
  alternateMobileNumber: string;
}