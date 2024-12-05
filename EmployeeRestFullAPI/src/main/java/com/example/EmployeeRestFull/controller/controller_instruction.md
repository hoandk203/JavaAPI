# Hướng Dẫn Xây Dựng `EmployeeController` với Spring Boot

Lớp `EmployeeController` đảm nhận vai trò là REST Controller, nhận và xử lý các yêu cầu HTTP từ phía client. Nó tương tác với `EmployeeService` để thực hiện các thao tác CRUD (tạo, đọc, cập nhật, xóa) cho thực thể `Employee`.

## Mục Tiêu

- Tạo và quản lý các API để thực hiện các thao tác CRUD với `Employee`.
- Sử dụng `EmployeeService` để tách biệt logic nghiệp vụ khỏi lớp controller.
- Trả về phản hồi HTTP phù hợp để thông báo kết quả cho client.

## Khởi Tạo Lớp `EmployeeController`

Dưới đây là mã nguồn của lớp `EmployeeController`:

```java
package com.example.EmployeeRestFull.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.EmployeeRestFull.entity.Employee;
import com.example.EmployeeRestFull.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

// Tạo một lớp EmployeeController để xử lý các yêu cầu liên quan đến nhân viên
@AllArgsConstructor
@RestController
@CrossOrigin // Cho phép các yêu cầu từ các miền khác
@RequestMapping("api/employees") // Đường dẫn cơ sở cho tất cả các yêu cầu trong controller
public class EmployeeController {

    private final EmployeeService employeeService; // Dịch vụ để xử lý logic nghiệp vụ

    // Lấy danh sách tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employees = employeeService.getAllEmployees(); // Gọi dịch vụ để lấy danh sách nhân viên
        return new ResponseEntity<>(employees, HttpStatus.OK); // Trả về danh sách nhân viên với mã trạng thái 200 (OK)
    }

    // Lấy thông tin nhân viên theo ID
    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> existEmployee = Optional.ofNullable(employeeService.getEmployeeById(id)); // Kiểm tra xem nhân viên có tồn tại không
        if (existEmployee.isPresent()) {
            return new ResponseEntity<>(existEmployee.get(), HttpStatus.OK); // Trả về thông tin nhân viên với mã trạng thái 200
        } else {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND); // Trả về thông báo lỗi với mã trạng thái 404
        }
    }

    // Tạo mới một nhân viên
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        // Kiểm tra xem dữ liệu nhân viên có hợp lệ không
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream() // Lấy thông báo lỗi từ BindingResult
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2) // Kết hợp các thông báo lỗi thành một chuỗi
                    .orElse("Validation error");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 (BAD REQUEST)
        }

        try {
            Employee employeeData = employeeService.postEmployee(employee); // Gọi dịch vụ để lưu nhân viên
            return new ResponseEntity<>(employeeData, HttpStatus.CREATED); // Trả về thông tin nhân viên mới với mã trạng thái 201 (CREATED)
        } catch (Exception e) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST); // Nếu có lỗi (ví dụ, email đã được sử dụng), trả về thông báo lỗi
        }
    }

    // Xóa nhân viên theo ID
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployeeById(id)); // Kiểm tra xem nhân viên có tồn tại không
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id); // Gọi dịch vụ để xóa nhân viên
            return new ResponseEntity<>("Employee successfully deleted", HttpStatus.OK); // Trả về thông báo thành công với mã trạng thái 200
        } else {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND); // Trả về thông báo lỗi với mã trạng thái 404
        }
    }

    // Cập nhật thông tin nhân viên
    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable("id") Long id, @Valid @RequestBody Employee employee, BindingResult bindingResult) {
        // Kiểm tra xem dữ liệu nhân viên có hợp lệ không
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream() // Lấy thông báo lỗi từ BindingResult
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2) // Kết hợp các thông báo lỗi thành một chuỗi
                    .orElse("Validation error");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400
        }

        Optional<Employee> existingEmployee = Optional.ofNullable(employeeService.getEmployeeById(id)); // Kiểm tra xem nhân viên có tồn tại không
        if (existingEmployee.isPresent()) {
            employee.setId(id); // Gán ID cho đối tượng nhân viên để cập nhật
            try {
                Employee updatedEmployee = employeeService.putEmployee(employee); // Gọi dịch vụ để cập nhật thông tin nhân viên
                return new ResponseEntity<>(updatedEmployee, HttpStatus.OK); // Trả về thông tin nhân viên đã cập nhật với mã trạng thái 200
            } catch (Exception e) {
                return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST); // Nếu có lỗi, trả về thông báo lỗi
            }
        } else {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND); // Trả về thông báo lỗi nếu nhân viên không tồn tại
        }
    }
}

```

## Giải Thích Chi Tiết

### 1. Annotations Sử Dụng trong `EmployeeController`

- **`@RestController`**: Đánh dấu lớp này là một REST Controller, giúp tự động chuyển đổi các đối tượng trả về thành JSON để phù hợp với các yêu cầu RESTful.
- **`@RequestMapping("api/employees")`**: Đặt đường dẫn gốc cho các endpoint của controller. Các yêu cầu sẽ bắt đầu với `/api/employees`.
- **`@CrossOrigin`**: Cho phép các yêu cầu từ các nguồn khác nhau (cross-origin), cần thiết khi client và server chạy trên các domain hoặc port khác nhau.
- **`@AllArgsConstructor`**: Tự động tạo constructor chứa tất cả các trường `final`, giúp Spring Boot dễ dàng quản lý dependency injection cho `employeeService`.

### 2. Các Phương Thức trong `EmployeeController`

#### Lấy Tất Cả Nhân Viên

- **Endpoint**: `GET /api/employees`
- **Phương Thức**: `getAllEmployee()`

  - **Logic**: Gọi `employeeService.getAllEmployees()` để lấy tất cả nhân viên từ cơ sở dữ liệu.

  - **Kết quả**: Trả về danh sách các nhân viên với mã HTTP `200 OK`.

#### Lấy Nhân Viên Theo ID

- **Endpoint**: `GET /api/employees/{id}`
- **Phương Thức**: `getEmployeeById(@PathVariable("id") Long id)`

  - **@PathVariable**: Liên kết ID từ URL với tham số `id` trong phương thức.

  - **Logic**: Gọi `employeeService.getEmployeeById(id)` để tìm nhân viên dựa trên ID.

  - **Kết quả**: Trả về nhân viên với ID tương ứng, hoặc thông báo lỗi nếu không tìm thấy với mã `404 NOT FOUND`.

#### Tạo Mới Một Nhân Viên

- **Endpoint**: `POST /api/employees`
- **Phương Thức**: `createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult)`

  - **@RequestBody**: Gắn nhân viên từ JSON gửi từ client.

  - **Logic**: Gọi `employeeService.postEmployee(employee)` để thêm mới nhân viên vào hệ thống. Nếu có lỗi (như email đã được sử dụng), trả về mã lỗi `400 BAD REQUEST`.

  - **Kết quả**: Trả về nhân viên vừa tạo với mã HTTP `201 CREATED`.

#### Xóa Nhân Viên Theo ID

- **Endpoint**: `DELETE /api/employees/{id}`
- **Phương Thức**: `deleteEmployeeById(@PathVariable("id") Long id)`

  - **@PathVariable**: Lấy ID của nhân viên cần xóa.

  - **Logic**: Kiểm tra xem nhân viên có tồn tại không. Nếu có, gọi `employeeService.deleteEmployee(id)` để xóa nhân viên khỏi cơ sở dữ liệu. Nếu không, trả về thông báo lỗi.

  - **Kết quả**: Trả về thông báo thành công hoặc lỗi với mã HTTP tương ứng (`200 OK` hoặc `404 NOT FOUND`).

#### Cập Nhật Thông Tin Nhân Viên

- **Endpoint**: `PUT /api/employees/{id}`
- **Phương Thức**: `updateEmployeeById(@PathVariable("id") Long id, @Valid @RequestBody Employee employee, BindingResult bindingResult)`

  - **@PathVariable**: Lấy ID của nhân viên cần cập nhật.

  - **@RequestBody**: Lấy dữ liệu cập nhật cho nhân viên.

  - **Logic**: Kiểm tra xem nhân viên có tồn tại không. Nếu có, gọi `employeeService.putEmployee(employee)` để cập nhật thông tin. Nếu có lỗi (như email đã được sử dụng), trả về mã lỗi `400 BAD REQUEST`.

  - **Kết quả**: Trả về nhân viên đã cập nhật với mã HTTP `200 OK` hoặc thông báo lỗi nếu không tìm thấy.

## Ví Dụ API

1. **Tạo Nhân Viên**

   ```bash
   POST /api/employees
   Content-Type: application/json
   Body: {
       "firstName": "Jane",
       "lastName": "Doe",
       "email": "janedoe@example.com"
   }
   ```

2. **Lấy Tất Cả Nhân Viên**

   ```bash
   GET /api/employees
   ```

3. **Lấy Nhân Viên Theo ID**

   ```bash
   GET /api/employees/1
   ```

4. **Cập Nhật Nhân Viên**

   ```

   ```

bash
PUT /api/employees/1
Content-Type: application/json
Body: {
"firstName": "Jane",
"lastName": "Smith",
"email": "janesmith@example.com"
}

````

5. **Xóa Nhân Viên**

```bash
DELETE /api/employees/1
````

## Kết Luận

Lớp `EmployeeController` cung cấp một bộ API mạnh mẽ cho phép quản lý thông tin nhân viên trong hệ thống. Với việc sử dụng `EmployeeService`, lớp này đảm bảo rằng các logic nghiệp vụ được tách biệt, giúp mã nguồn dễ bảo trì và mở rộng trong tương lai.

## Tổng Kết

- **`EmployeeController`** cung cấp các endpoint RESTful để thao tác với `Employee` (tạo mới, lấy tất cả, lấy theo ID, cập nhật, xóa).
- **Sử dụng `EmployeeService`** để xử lý logic nghiệp vụ, giúp mã nguồn dễ bảo trì và mở rộng.
- **Phản hồi HTTP hợp lệ** được trả về để thông báo kết quả cho client, sử dụng các mã trạng thái HTTP phù hợp (`201 CREATED`, `200 OK`, `404 NOT FOUND`, ...).
