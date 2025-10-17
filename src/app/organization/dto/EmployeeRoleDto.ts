export class EmployeeRoleDto{
    employeeRoleId!:Number;
    employeeRoleName!:string;
    constructor(employeeRoleId:number, employeeRoleName:string){
        this.employeeRoleId=employeeRoleId;
        this.employeeRoleName=employeeRoleName
    }
}