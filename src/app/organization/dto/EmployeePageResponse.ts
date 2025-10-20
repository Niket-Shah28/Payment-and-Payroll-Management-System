import { Employee } from "./Employee";

export interface EmployeePageResponse {
  content: Employee[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}