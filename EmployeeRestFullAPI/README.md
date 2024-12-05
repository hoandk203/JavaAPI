# Hướng Dẫn Sử Dụng Spring Boot RESTful API

## Mục Lục

1. [Giới Thiệu](#giới-thiệu)
2. [Cấu Trúc Dự Án](#cấu-trúc-dự-án)
3. [Cài Đặt](#cài-đặt)
4. [Chạy Ứng Dụng](#chạy-ứng-dụng)
5. [Cấu Hình Kết Nối Database](#cấu-hình-kết-nối-database)
6. [Ví Dụ Cụ Thể: Ứng Dụng Quản Lý Nhân Viên](#ví-dụ-cụ-thể-ứng-dụng-quản-lý-nhân-viên)

## Giới Thiệu

RESTful API là một kiểu thiết kế API cho phép các hệ thống giao tiếp với nhau thông qua HTTP bằng các phương thức như GET, POST, PUT, DELETE. Spring Boot là một framework trong Java giúp việc xây dựng các RESTful API trở nên dễ dàng hơn.

Ứng dụng Spring Boot RESTful cơ bản thường bao gồm các thành phần:

- `Controller`: Xử lý các yêu cầu HTTP và trả về phản hồi.
- `Service`: Chứa logic xử lý của ứng dụng.
- `Repository`: Tương tác với database để truy xuất và lưu trữ dữ liệu.

## Cấu Trúc Dự Án

Cấu trúc cơ bản của một dự án Spring Boot RESTful API thường bao gồm:

```
project-root
│
├── src/main/java/com/example/YourApp
│   ├── YourAppApplication.java  # File main để khởi động ứng dụng
│   ├── controller               # Thư mục chứa các lớp Controller
│   ├── service                  # Thư mục chứa các lớp Service
│   ├── repository               # Thư mục chứa các lớp Repository
│   └── entity                   # Thư mục chứa các lớp Entity (Entity)
│
├── src/main/resources
│   └── application.properties        # File cấu hình ứng dụng
│
└── pom.xml                           # File cấu hình Maven

```

## Cài Đặt

1. **Clone Dự Án**: Tải mã nguồn của dự án từ repository hoặc tạo một dự án Spring Boot mới.
2. **Thêm Dependency**: Thêm các dependency cần thiết vào `pom.xml` (như Spring Web, Spring Data JPA, MySQL Driver...).
3. **Cấu Hình Database**: Thiết lập các thông tin về kết nối database trong `application.properties`.

## Cấu Hình Kết Nối Database

Trong application.properties, cấu hình kết nối MySQL cơ bản có dạng:

```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Ví Dụ Cụ Thể: Ứng Dụng Quản Lý Nhân Viên

Dưới đây là ví dụ cho ứng dụng `EmployeeRestFullApplication` sử dụng Spring Boot để tạo API quản lý nhân viên.

### 1. Cấu Trúc Cụ Thể

```
EmployeeRestFull
│
├── src/main/java/com/example/EmployeeRestFull
│   ├── EmployeeRestFullApplication.java    # File main khởi động ứng dụng
│   ├── controller
│   │   └── EmployeeController.java         # Xử lý các yêu cầu API cho nhân viên
│   ├── service
│   │   └── EmployeeService.java            # Logic nghiệp vụ cho nhân viên
│   ├── repository
│   │   └── EmployeeRepository.java         # Tương tác với database cho nhân viên
│   └── entity
│       └── Employee.java                   # Định nghĩa mô hình dữ liệu của nhân viên
│
├── src/main/resources
│   └── application.properties              # File cấu hình ứng dụng
│
└── pom.xml                                 # File cấu hình Maven
```

### 2. File Khởi Động Ứng Dụng: `EmployeeRestFullApplication.java`

```java
// Khối mã Java với cú pháp tô màu
package com.example.EmployeeRestFull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeRestFullApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeRestFullApplication.class, args);
    }
}
```

### 3. API Cơ Bản cho Quản Lý Nhân Viên

**Entity**: Đại diện cho thực thể `Employee` trong database.

```java

package com.example.EmployeeRestFull.model;

import javax.persistence.\*;

@Entity
public class Employee {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String department;

    // Getter và Setter

}

```

**Repository**: Tương tác với database `Employee`.

```java
package com.example.EmployeeRestFull.repository;

import com.example.EmployeeRestFull.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
}
```

**Service**: Xử lý logic nghiệp vụ của `Employee`.

```java

package com.example.EmployeeRestFull.service;

import com.example.EmployeeRestFull.model.Employee;
import com.example.EmployeeRestFull.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Thêm logic nghiệp vụ khác nếu cần

}

```

**Controller**: Định nghĩa các endpoint cho các thao tác CRUD (Create, Read, Update, Delete) trên `Employee`.

```java

package com.example.EmployeeRestFull.controller;

import com.example.EmployeeRestFull.model.Employee;
import com.example.EmployeeRestFull.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.\*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // Tương tự, tạo các phương thức cho `update` và `delete`

}

```

## Tham khảo thêm

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---
