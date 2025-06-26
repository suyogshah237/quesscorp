package com.quesscorp.controller;

import com.quesscorp.model.Employee;
import com.quesscorp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable String departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartmentId(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    @PostMapping("/department/{departmentId}")
    public ResponseEntity<Employee> addEmployeeToDepartment(
            @PathVariable String departmentId,
            @RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.addEmployeeToDepartment(departmentId, employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/department/{departmentId}/employee/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeFromDepartment(
            @PathVariable String departmentId,
            @PathVariable String employeeId) {
        try {
            employeeService.deleteEmployeeFromDepartment(departmentId, employeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employeeService.deleteEmployee(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
