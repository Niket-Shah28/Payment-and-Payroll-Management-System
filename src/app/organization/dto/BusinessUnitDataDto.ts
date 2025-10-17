export class BusinessUnitDataDto{
    businessUnitId!:Number;
    businessUnitName!:string;
    constructor(businessUnitId:number, businessUnitName:string){
        this.businessUnitId=businessUnitId;
        this.businessUnitName=businessUnitName
    }
}