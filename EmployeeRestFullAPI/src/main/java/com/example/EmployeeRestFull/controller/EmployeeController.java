package com.example.EmployeeRestFull.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.EmployeeRestFull.entity.Employee;
import com.example.EmployeeRestFull.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/employees")
public class EmployeeController {

    EmployeeService employeeService;

    // Lấy danh sách tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Lấy thông tin nhân viên theo ID
    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> existEmployee = Optional.ofNullable(employeeService.getEmployeeById(id));
        if (existEmployee.isPresent()) {
            return new ResponseEntity<>(existEmployee.get(), HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Employee not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Tạo mới một nhân viên
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation error");
            errorResponse.put("details", bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2)
                    .orElse("Unknown validation error"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        
        try {
            Employee employeeData = employeeService.postEmployee(employee);
            return new ResponseEntity<>(employeeData, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Email is already in use");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa nhân viên theo ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployeeById(id));
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Employee successfully deleted");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Employee not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Cập nhật thông tin nhân viên
    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable("id") Long id, @Valid @RequestBody Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation error");
            errorResponse.put("details", bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2)
                    .orElse("Unknown validation error"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        
        Optional<Employee> existingEmployee = Optional.ofNullable(employeeService.getEmployeeById(id));
        if (existingEmployee.isPresent()) {
            employee.setId(id);
            try {
                Employee updatedEmployee = employeeService.putEmployee(employee);
                return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Email is already in use");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Employee not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Tìm kiếm nhân viên theo từ khóa trong tất cả các trường
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("keyword") String keyword) {
        List<Employee> employees = employeeService.searchEmployees(keyword);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
