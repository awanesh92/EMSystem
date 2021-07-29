import { Injectable } from '@angular/core';
import { Employee } from '../entity/Employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  // In-memory list of employees
  allEmployees: Employee[] = [
    {
      id: '1',
      firstname: 'Awanesh',
      lastname: 'Yadav',
      age: 28,
      designation: 'Developer',
      department: 'HR'
    }
  ];

  // Returns all the employees
  getAllEmployees(): Employee[] {
    return this.allEmployees;
  }

  // Adds an employee to employee list
  addEmployee(employee: Employee) {
    this.allEmployees.push(employee);
  }

  // Update employee details
  updateEmployee(employee: Employee) {
    // tslint:disable-next-line:prefer-const
    let updateEmployee = this.allEmployees.find(emp => emp.id == employee.id);
    updateEmployee.firstname = employee.firstname;
    updateEmployee.lastname = employee.lastname;
    updateEmployee.age = employee.age;
    updateEmployee.designation = employee.designation;
    updateEmployee.department = employee.department;
  }

  // Deletes an employee from employee list
  deleteEmployee(id: string) {
    this.allEmployees = this.allEmployees.filter(employee => employee.id != id);
  }

  // Returns an employee with passed employee id from employee list
  getEmployee(department: string): Employee {
    return this.allEmployees.find(emp => emp.department == department);
  }
}
