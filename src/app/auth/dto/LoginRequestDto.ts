export class LoginRequestDto{
    referenceId!:string
    password!:string
    constructor(referenceId:string, password:string){
        this.referenceId = referenceId;
        this.password = password;
    }
}