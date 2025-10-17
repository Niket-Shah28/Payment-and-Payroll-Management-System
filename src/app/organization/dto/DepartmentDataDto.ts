export class DepartmentDataDto{
    departmentId!:Number;
    departmentName!:string;
    constructor(departmentId:number, departmentName:string){
        this.departmentId=departmentId;
        this.departmentName=departmentName
    }
}