package com.quesscorp.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Department {
    
    @Id
    private String id;
    private String name;
    private String location;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Employee> employees = new ArrayList<>();
    
    // Constructors
    public Department() {
    }
    
    public Department(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        
        // Set bidirectional relationship
        if (employees != null) {
            for (Employee employee : employees) {
                employee.setDepartment(this);
            }
        }
    }
    
    // Helper method to add an employee
    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
        employee.setDepartment(this);
    }
    
    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", employeesCount=" + (employees != null ? employees.size() : 0) +
                '}';
    }
}
