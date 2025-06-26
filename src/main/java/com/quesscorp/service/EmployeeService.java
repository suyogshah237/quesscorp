package com.quesscorp.service;

import com.quesscorp.model.Employee;
import com.quesscorp.repository.DepartmentRepository;
import com.quesscorp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }
    
    public List<Employee> getEmployeesByDepartmentId(String departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
    
    public Employee addEmployeeToDepartment(String departmentId, Employee employee) {
        return departmentRepository.findById(departmentId)
                .map(department -> {
                    employee.setDepartment(department);
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
    }
    
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }
    
    public void deleteEmployeeFromDepartment(String departmentId, String employeeId) {
        employeeRepository.findById(employeeId)
                .ifPresent(employee -> {
                    if (employee.getDepartment() != null && 
                        employee.getDepartment().getId().equals(departmentId)) {
                        employeeRepository.deleteById(employeeId);
                    } else {
                        throw new RuntimeException("Employee not found in the specified department");
                    }
                });
    }
}
