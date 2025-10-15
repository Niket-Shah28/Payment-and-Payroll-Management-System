export class LoginRequestDto{
    referenceId!:string
    password!:string
    capatchaResponse!:string
    constructor(referenceId:string, password:string, capatchaResponse:string){
        this.referenceId = referenceId;
        this.password = password;
        this.capatchaResponse=capatchaResponse;
    }
}