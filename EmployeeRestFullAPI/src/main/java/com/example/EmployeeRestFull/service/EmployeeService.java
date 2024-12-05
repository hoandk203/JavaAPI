package com.example.EmployeeRestFull.service;

import java.util.List;
import com.example.EmployeeRestFull.entity.Employee;

public interface EmployeeService {
    
    // Thêm mới một Employee
    Employee postEmployee(Employee employee);
    
    // Lấy danh sách tất cả các Employee
    List<Employee> getAllEmployees();
    
    // Xóa Employee theo ID
    void deleteEmployee(Long employeeId);
    
    // Lấy Employee theo ID
    Employee getEmployeeById(Long employeeId);
    
    // Cập nhật thông tin Employee
    Employee putEmployee(Employee employee);
    
    // Tìm kiếm trên tất cả các trường
    List<Employee> searchEmployees(String keyword);
}
