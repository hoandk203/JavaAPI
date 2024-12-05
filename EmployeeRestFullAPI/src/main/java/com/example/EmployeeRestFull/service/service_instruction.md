# Hướng Dẫn Sử Dụng Lớp Service Trong Spring Boot

Trong ứng dụng Spring Boot, lớp Service thường được chia thành hai phần chính:

1. **Interface** (`EmployeeService`) - định nghĩa các phương thức mà service cần cung cấp.
2. **Implementation** (`EmployeeServiceImpl`) - hiện thực các phương thức trong interface, chứa logic chi tiết.

## Mục Đích

Việc tách lớp Service thành interface và implementation giúp:

- Tăng tính linh hoạt cho ứng dụng, cho phép thay đổi hoặc mở rộng các phương thức trong lớp hiện thực mà không ảnh hưởng đến các thành phần khác.
- Dễ dàng viết unit test và mock các phương thức của Service.

## Cấu Trúc

### 1. Interface `EmployeeService`

Interface này khai báo các phương thức để thao tác với thực thể `Employee`. Các phương thức bao gồm thêm mới, lấy tất cả, xóa, lấy theo ID, và cập nhật `Employee`.

#### Ví dụ Interface `EmployeeService`

```java
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
}
```

### 2. Implementation `EmployeeServiceImpl`

Lớp này hiện thực các phương thức trong `EmployeeService`, sử dụng `EmployeeRepository` để tương tác với cơ sở dữ liệu. Đây là nơi chứa logic chi tiết cho từng thao tác.

#### Ví dụ Implementation `EmployeeServiceImpl`

```java
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
}
```

### Giải Thích Chi Tiết

- **Tiêm Phụ Thuộc `EmployeeRepository`**: Sử dụng `@Autowired` để Spring Boot tự động tiêm phụ thuộc vào `EmployeeRepository`. Điều này giúp `EmployeeServiceImpl` có thể sử dụng các phương thức của `EmployeeRepository` để thao tác với cơ sở dữ liệu.
- **Phương Thức `postEmployee`**:

  - Dùng để thêm một `Employee` mới vào cơ sở dữ liệu.
  - Sử dụng phương thức `save` từ `JpaRepository`.

- **Phương Thức `getAllEmployees`**:

  - Trả về danh sách tất cả `Employee` trong cơ sở dữ liệu.
  - Sử dụng phương thức `findAll`.

- **Phương Thức `deleteEmployee`**:

  - Xóa `Employee` theo `employeeId`.
  - Sử dụng phương thức `deleteById`.

- **Phương Thức `getEmployeeById`**:

  - Tìm kiếm `Employee` dựa trên `employeeId`.
  - Trả về `null` nếu không tìm thấy.

- **Phương Thức `putEmployee`**:
  - Cập nhật thông tin `Employee` nếu `employeeId` tồn tại.
  - Trả về `null` nếu không tìm thấy `Employee` để cập nhật.

## Sử Dụng Service trong Controller

Để sử dụng `EmployeeService` trong Controller, ta có thể tiêm nó vào Controller như sau:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.EmployeeRestFull.entity.Employee;
import com.example.EmployeeRestFull.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.postEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.putEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
```

Trong Controller:

- Các phương thức HTTP như `POST`, `GET`, `PUT`, và `DELETE` được ánh xạ đến các phương thức tương ứng trong `EmployeeService` để thực hiện các thao tác CRUD.

---

## Tổng Kết

- **Interface `EmployeeService`**: Định nghĩa các phương thức cần triển khai.
- **Implementation `EmployeeServiceImpl`**: Hiện thực các phương thức từ `EmployeeService` bằng cách sử dụng `EmployeeRepository` để thao tác với cơ sở dữ liệu.
- **Controller**: Sử dụng `EmployeeService` để cung cấp API cho các thao tác CRUD của `Employee`.

Cấu trúc này giúp ứng dụng Spring Boot dễ bảo trì, mở rộng và kiểm thử.
