package com.quesscorp.service;

import com.quesscorp.model.Department;
import com.quesscorp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    public Optional<Department> getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }
    
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }
}
