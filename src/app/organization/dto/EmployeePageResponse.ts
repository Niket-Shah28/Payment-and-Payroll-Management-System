import { Employee } from "./Employee";

export interface EmployeePageResponse {
  content: Employee[];
  hasNextPage: boolean;
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}