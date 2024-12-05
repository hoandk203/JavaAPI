package com.example.EmployeeRestFull.service.impl;

import com.example.EmployeeRestFull.entity.Employee;
import com.example.EmployeeRestFull.repository.EmployeeRepository;
import com.example.EmployeeRestFull.service.EmployeeService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    // Thêm mới Employee
    @Override
    public Employee postEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Lấy tất cả Employee
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Xóa Employee theo ID
    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    // Lấy Employee theo ID
    @Override
    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.orElse(null); // Trả về null nếu không tìm thấy
    }

    // Cập nhật Employee
    @Override
    public Employee putEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            return employeeRepository.save(employee); // Cập nhật nếu Employee đã tồn tại
        }
        return null; // Trả về null nếu Employee không tồn tại
    }
    
    // Tìm kiếm trên tất cả các trường
    @Override
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchByKeyword(keyword);
    }
}
