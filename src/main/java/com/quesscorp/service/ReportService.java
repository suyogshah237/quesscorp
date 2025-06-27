package com.quesscorp.service;

import com.quesscorp.model.Department;
import com.quesscorp.model.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ReportService {

    private final DepartmentService departmentService;

    public ReportService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public byte[] generateEmployeesByDepartmentReport() throws JRException, IOException {
        List<Department> departments = departmentService.getAllDepartments();
        
        // Main report
        InputStream masterReportStream = new ClassPathResource("reports/departments_master.jrxml").getInputStream();
        JasperDesign masterDesign = JRXmlLoader.load(masterReportStream);
        JasperReport masterReport = JasperCompileManager.compileReport(masterDesign);
        
        // Department subreport
        InputStream departmentReportStream = new ClassPathResource("reports/department_employees.jrxml").getInputStream();
        JasperDesign departmentDesign = JRXmlLoader.load(departmentReportStream);
        JasperReport departmentReport = JasperCompileManager.compileReport(departmentDesign);
        
        // Parameters for the master report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SUBREPORT_DIR", "reports/");
        parameters.put("DEPARTMENT_SUBREPORT", departmentReport);
        
        // Create a list to pass to the master report
        List<Map<String, Object>> reportData = new ArrayList<>();
        
        // For each department, create a map with department data and employees list
        for (Department department : departments) {
            Map<String, Object> departmentMap = new HashMap<>();
            departmentMap.put("departmentId", department.getId());
            departmentMap.put("departmentName", department.getName());
            departmentMap.put("departmentLocation", department.getLocation());
            departmentMap.put("employees", new JRBeanCollectionDataSource(department.getEmployees()));
            reportData.add(departmentMap);
        }
        
        // Create data source for the master report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
        
        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parameters, dataSource);
        
        // Export to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
