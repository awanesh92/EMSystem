export class Employee {
    id:string;
    firstname:string;
    lastname:string;
    age:number;
    designation:string;
    department:string;

    constructor(id:string,
        firstname:string,
        lastname:string,
        age:number,
        designation:string,
        department:string){
            this.id = id;
            this.firstname = firstname;
            this.lastname = lastname;
            this.age = age;
            this.designation = designation;
            this.department = department;
        }
}