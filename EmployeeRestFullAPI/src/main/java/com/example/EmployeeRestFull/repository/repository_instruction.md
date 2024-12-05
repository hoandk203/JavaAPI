# Hướng Dẫn Sử Dụng Các Thuộc Tính Phổ Biến trong Repository của Spring Boot

Để tạo một `repository` tổng quát có thể dùng cho nhiều loại `entity` khác nhau, chúng ta có thể tạo một interface chung gọi là `BaseRepository` với các phương thức CRUD cơ bản. Interface này sẽ sử dụng kiểu dữ liệu `generic` để đại diện cho các loại `entity` khác nhau, cho phép tái sử dụng mà không cần phải viết lại repository cho từng đối tượng.

Dưới đây là cách triển khai `BaseRepository` tổng quát và cách sử dụng nó với `Employee`.

---

### Tạo `BaseRepository` Tổng Quát

Đầu tiên, tạo một interface `BaseRepository` sử dụng generic `<T, ID>`, trong đó:

- `T` là kiểu của `entity` (ví dụ: `Employee`, `Department`,...).
- `ID` là kiểu của khóa chính của `entity` (ví dụ: `Long`, `String`,...).

```java
package com.example.helloRestFull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // Các phương thức CRUD cơ bản được cung cấp bởi JpaRepository
    // Có thể thêm các phương thức chung khác ở đây nếu cần
}
```

#### Giải Thích

- **`BaseRepository<T, ID extends Serializable>`**: Định nghĩa một repository tổng quát cho bất kỳ `entity` nào (`T`) với một khóa chính (`ID`) phải triển khai từ `Serializable`.
- **`extends JpaRepository<T, ID>`**: Kế thừa từ `JpaRepository`, do đó, `BaseRepository` sẽ có sẵn các phương thức CRUD như `save`, `findById`, `findAll`, `deleteById`,...

### Tóm Tắt

- **`BaseRepository<T, ID>`** là một repository tổng quát giúp tái sử dụng cho các `entity` khác nhau.
- **`EmployeeRepository`** kế thừa từ `BaseRepository` cho phép thao tác với `Employee`.
- **Thêm các phương thức tùy chỉnh** trong `BaseRepository` sẽ giúp dùng chung các phương thức cho nhiều `entity`.

Sử dụng `BaseRepository` giúp giảm bớt sự trùng lặp khi làm việc với nhiều `entity` và giúp quản lý mã nguồn dễ dàng hơn trong các dự án lớn.

Dưới đây là hướng dẫn chi tiết cho lớp `EmployeeRepository` trong Spring Boot, bao gồm các thành phần cơ bản và những phương pháp phổ biến khi làm việc với repository trong JPA.

---

# Employee Repository

Lớp `EmployeeRepository` là một interface trong Spring Boot, mở rộng từ `JpaRepository`, cung cấp các phương thức để thực hiện các thao tác CRUD (Create, Read, Update, Delete) với entity `Employee`. Khi sử dụng `JpaRepository`, Spring Data JPA sẽ tự động tạo các truy vấn cơ bản mà không cần viết mã SQL thủ công.

## Khởi Tạo Repository

```java
package com.example.EmployeeRestFull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeRestFull.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
```

### Giải Thích

- **`JpaRepository<Employee, Long>`**: Định nghĩa `EmployeeRepository` sẽ thao tác với entity `Employee`, với `Long` là kiểu dữ liệu của khóa chính (`id`) trong `Employee`.
- **Interface**: Đây là một interface, không cần triển khai các phương thức. Spring Boot sẽ tự động tạo ra các phương thức cho các thao tác CRUD.

### Các Phương Thức CRUD Mặc Định

Khi mở rộng từ `JpaRepository`, `EmployeeRepository` tự động có các phương thức sau:

1. **Lấy tất cả dữ liệu**:

   - `List<Employee> findAll();`

2. **Tìm kiếm theo ID**:

   - `Optional<Employee> findById(Long id);`

3. **Lưu hoặc cập nhật một đối tượng**:

   - `Employee save(Employee employee);`

4. **Xóa theo ID**:

   - `void deleteById(Long id);`

5. **Kiểm tra sự tồn tại theo ID**:
   - `boolean existsById(Long id);`

### Các Phương Thức Tùy Chỉnh

Ngoài các phương thức CRUD mặc định, bạn có thể khai báo thêm các phương thức tùy chỉnh trong `EmployeeRepository` dựa trên quy tắc đặt tên của Spring Data JPA. Ví dụ:

1. **Tìm kiếm theo tên**:

   ```java
   List<Employee> findByFirstName(String firstName);
   ```

2. **Tìm kiếm theo tên và họ**:

   ```java
   List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
   ```

3. **Tìm kiếm theo tên chứa từ khóa**:

   ```java
   List<Employee> findByFirstNameContaining(String keyword);
   ```

4. **Tìm kiếm với phân trang và sắp xếp**:
   ```java
   Page<Employee> findByLastName(String lastName, Pageable pageable);
   ```

### Tạo Truy Vấn SQL Tùy Chỉnh

Đôi khi bạn cần các truy vấn phức tạp hơn không thể biểu diễn bằng quy tắc đặt tên. Trong trường hợp này, bạn có thể sử dụng `@Query`:

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

   @Query("SELECT e FROM Employee e " +
         "WHERE (CAST(e.id AS string) LIKE CONCAT('%', :keyword, '%')) " +
         "OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
         "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
         "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
   List<Employee> searchByKeyword(@Param("keyword") String keyword);

}
```

- **@Query**: Xác định một truy vấn JPQL (Java Persistence Query Language).
- **@Param**: Đánh dấu tham số sử dụng trong truy vấn JPQL.

### Sử Dụng Repository trong Service

Để sử dụng `EmployeeRepository` trong service, chỉ cần khai báo và Spring Boot sẽ tự động tiêm phụ thuộc thông qua `@Autowired`:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
```

---

## Tổng Kết

- **EmployeeRepository** là một interface mở rộng từ `JpaRepository` để thao tác với entity `Employee`.
- Tự động có các phương thức CRUD mà không cần viết mã SQL.
- Có thể tạo các phương thức tùy chỉnh bằng cách tuân theo quy tắc đặt tên hoặc sử dụng `@Query` để định nghĩa truy vấn phức tạp.

Sử dụng `EmployeeRepository` giúp đơn giản hóa các thao tác với cơ sở dữ liệu, giúp mã nguồn rõ ràng và dễ bảo trì.
