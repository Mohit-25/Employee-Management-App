import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

//import { Component } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';


@Component({
    selector: 'app-employee-list',
    templateUrl:'./employee-list.component.html',
    styleUrls: ['./employee-list.component.css']
    
})

export class EmployeeListComponent implements OnInit
{

    employees: Employee[];

    constructor(private employeeService: EmployeeService,
        private router : Router) {}

    deleteMessage = false;

    

    ngOnInit(): void 
    {
       
        this.getEmployees();
    }

    private getEmployees()
    {
        this.employeeService.getEmployeesList().subscribe(data =>{
            this.employees = data;
        });
    }

    updateEmployee(id:number){

        this.router.navigate(['update-employee', id]);
    }

    deleteEmployee(id:number)
    {
        this.employeeService.deleteEmployee(id).subscribe(data =>{
            console.log(data);
            this.deleteMessage = true;
            this.getEmployees();
        })
    }

    employeeDetails(id: number)
    {
        this.router.navigate(['employee-details', id]);
    }

    removeMessage()
    {
        this.deleteMessage=false;
    }

   

}
