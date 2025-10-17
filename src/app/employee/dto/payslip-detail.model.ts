export interface PayslipDetail {
  payslipMonth: string;
  year: number;
  employeeId: number;
  employeeName: string;
  department: string;
  role: string;
  businessUnit: string;
  accountNumber: string;
  ifscCode: string;
  bankName: string;
  basicSalary: number;
  houseRentAllowance: number;
  dearnessAllowance: number;
  otherAllowances: number;
  actualSalary: number;
  providentFund: number;
  finalSalary: number;
}
