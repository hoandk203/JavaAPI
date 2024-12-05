# Hướng Dẫn Sử Dụng Các Thuộc Tính Phổ Biến trong Entity của Spring Boot

Trong Spring Boot, để định nghĩa các entity (thực thể) trong ứng dụng, ta sử dụng các chú thích (annotations) từ `Jakarta Persistence API` để ánh xạ lớp với bảng trong cơ sở dữ liệu. Dưới đây là các chú thích phổ biến thường dùng cho các thuộc tính trong entity, cũng như giải thích và cách sử dụng của chúng.

## Cấu Trúc Class

```java
package com.example.EmployeeRestFull.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "firstName", length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "lastName", length = 50)
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    // @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must be alphanumeric")
    // private String username;

    // @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    // @Pattern(regexp = "^\\d{10,15}$", message = "Phone number must be numeric")
    // private String phoneNumber;
}

```

## Chi Tiết Các Chú Thích Sử Dụng

### 1. `@Entity`

- `@Entity`: Đánh dấu lớp này là một entity và sẽ được ánh xạ với bảng trong cơ sở dữ liệu.
- Khi sử dụng `@Entity`, Spring JPA sẽ tự động nhận diện và quản lý các đối tượng Employee dưới dạng các bản ghi trong bảng `employees`.

### 2. `@Table`

- `@Table(name = "employees")`: Xác định tên bảng mà entity này sẽ ánh xạ tới. Nếu không chỉ định @Table, tên mặc định của bảng sẽ là tên của class (`Employee`).
- Trong ví dụ này, bảng được đặt tên là `employees`.

### 3.` @Id` và `@GeneratedValue`

- `@Id`: Đánh dấu thuộc tính `id` là khóa chính của entity.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Đặt thuộc tính id tự động tăng dần. `GenerationType.IDENTITY` là chiến lược phổ biến để sử dụng cho các cột khóa chính có kiểu tự tăng trong cơ sở dữ liệu.

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
```

### 4. `@Column`

- `@Column(name = "column_name")`: Ánh xạ thuộc tính với cột trong cơ sở dữ liệu. Tên cột có thể được tùy chỉnh bằng cách sử dụng thuộc tính `name`.
- Các thuộc tính bổ sung phổ biến của `@Column`:
  - `nullable = false`: Không cho phép giá trị null cho cột.
  - `unique = true`: Đảm bảo giá trị trong cột là duy nhất.
  - `length = value`: Chỉ định độ dài tối đa cho chuỗi.
  - `precision và scale`: Dùng cho các số thập phân, ví dụ` @Column(precision = 10, scale = 2)`.

```java
@Column(name = "email", nullable = false, unique = true, length = 100)
private String email;
```

### 5. `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` từ **Lombok**

- **Lombok** giúp tạo ra tự động các phương thức getter, setter, và constructor.
  - `@Getter` và `@Setter`: Tự động tạo getter và setter cho tất cả các thuộc tính trong class.
  - `@NoArgsConstructor`: Tạo constructor không tham số.
  - `@AllArgsConstructor`: Tạo constructor với tất cả các tham số, tương ứng với các thuộc tính của class.

Dưới đây là phần mở rộng cho mục 6 với các ví dụ cụ thể cho các kiểu dữ liệu như điện thoại, tuổi, và email, sử dụng các chú thích từ Jakarta Validation API để kiểm tra tính hợp lệ của dữ liệu đầu vào.

### 6. Các Chú Thích Kiểm Tra Dữ Liệu

Trong Spring Boot, các chú thích kiểm tra dữ liệu giúp đảm bảo tính hợp lệ của dữ liệu đầu vào trước khi được lưu vào cơ sở dữ liệu. Dưới đây là một số chú thích phổ biến, cùng với các ví dụ cho các kiểu dữ liệu như điện thoại, tuổi, và email.

#### 6.1 `@NotBlank`

- **Mô tả**: Đảm bảo rằng trường không chỉ là một chuỗi rỗng mà còn không chỉ chứa khoảng trắng.
- **Cách sử dụng**:

```java
@NotBlank(message = "First name is required")
private String firstName;
```

#### 6.2 `@Size`

- **Mô tả**: Giới hạn kích thước của chuỗi hoặc số.
- **Cách sử dụng**:

```java
@Size(max = 50, message = "First name must not exceed 50 characters")
private String firstName;

@Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
private String phoneNumber; // Số điện thoại
```

#### 6.3 `@Email`

- **Mô tả**: Kiểm tra định dạng email hợp lệ.
- **Cách sử dụng**:

```java
@Email(message = "Email should be valid")
private String email;
```

#### 6.4 `@NotNull`

- **Mô tả**: Đảm bảo rằng giá trị không được là `null`.
- **Cách sử dụng**:

```java
@NotNull(message = "Department is required")
private Department department;
```

#### 6.5 `@Min` và `@Max`

- **Mô tả**: Xác định giá trị tối thiểu và tối đa cho một số nguyên hoặc số thực.
- **Cách sử dụng**:

```java
@Min(value = 18, message = "Age must be at least 18")
private Integer age; // Tuổi

@Max(value = 100, message = "Age must not exceed 100")
private Integer age; // Giới hạn tối đa cho tuổi
```

#### 6.6 `@Pattern`

- **Mô tả**: Kiểm tra chuỗi dựa trên một biểu thức chính quy (regex).
- **Cách sử dụng**:

```java
@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must be alphanumeric")
private String username;

// Giải thích biểu thức chính quy:
// ^: Bắt đầu của chuỗi.
// [A-Za-z0-9]: Ký tự được phép là chữ cái (cả hoa và thường) hoặc số.
// +: Ít nhất một ký tự phải có, nghĩa là chuỗi không được rỗng.
// $: Kết thúc của chuỗi.

@Pattern(regexp = "^\\d{10,15}$", message = "Phone number must be numeric and between 10 to 15 digits")
private String phoneNumber; // Số điện thoại phải là chữ số và có độ dài từ 10 đến 15
```

#### 6.7 `@Future` và `@Past`

- **Mô tả**: Kiểm tra một ngày có nằm trong tương lai hoặc trong quá khứ.
- **Cách sử dụng**:

```java
@Future(message = "Start date must be in the future")
private LocalDate startDate;

@Past(message = "End date must be in the past")
private LocalDate endDate;
```

### Ví Dụ Tổng Hợp

Dưới đây là một ví dụ về một lớp Java với các thuộc tính sử dụng các chú thích kiểm tra dữ liệu:

```java
import jakarta.validation.constraints.*;

public class User {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Pattern(regexp = "^\\d{10,15}$", message = "Phone number must be numeric")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must not exceed 100")
    private Integer age;

    // Constructors, getters, setters
}
```

## Các Thuộc Tính Phổ Biến Khác Có Thể Sử Dụng

### 1. `@ManyToOne`, `@OneToMany`, `@OneToOne`, và `@ManyToMany`([Xem thêm ở phần mở rộng cách sử dụng](#ví-dụ-manytoone-onetomany-onetoone-và-manytomany))

Các chú thích thiết lập quan hệ giữa các entity:

- `@ManyToOne`: Nhiều bản ghi của một entity này có thể liên kết với một bản ghi của entity khác.
- `@OneToMany`: Một bản ghi của một entity có thể liên kết với nhiều bản ghi của entity khác.
- `@OneToOne`: Một bản ghi của một entity liên kết với một bản ghi của entity khác.
- `@ManyToMany`: Nhiều bản ghi của một entity có thể liên kết với nhiều bản ghi của entity khác.

```java
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;

@OneToMany(mappedBy = "department")
private List<Employee> employees;

@OneToOne
@JoinColumn(name = "profile_id")
private Profile profile;

@ManyToMany
@JoinTable(
    name = "employee_project",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id")
)
private Set<Project> projects;
```

### 2. `@Temporal`

- `@Temporal`: Dùng để ánh xạ kiểu Date hoặc Calendar trong Java với các kiểu dữ liệu ngày trong cơ sở dữ liệu.
  - `@Temporal(TemporalType.DATE)`: Lưu chỉ ngày.
  - `@Temporal(TemporalType.TIME)`: Lưu chỉ giờ.
  - `@Temporal(TemporalType.TIMESTAMP)`: Lưu cả ngày và giờ.

```java
@Temporal(TemporalType.DATE)
@Column(name = "birth_date")
private Date birthDate;
```

### 3. `@Enumerated`

- `@Enumerated`: Sử dụng khi lưu các giá trị enum vào cột.
  - `EnumType.STRING`: Lưu giá trị enum dưới dạng chuỗi.
  - `EnumType.ORDINAL`: Lưu giá trị enum dưới dạng chỉ số (số nguyên).

```java
@Enumerated(EnumType.STRING)
@Column(name = "status")
private Status status;

public enum Status {
    ACTIVE,
    INACTIVE
}
```

### 4. `@Lob`

- `@Lob`: Dùng cho các trường dữ liệu lớn, ví dụ như chuỗi văn bản dài hoặc dữ liệu nhị phân.

```java
@Lob
@Column(name = "description")
private String description;
```

### 5. `@Embedded` và `@Embeddable`

- `@Embedded`: Được sử dụng để nhúng một class khác vào trong một entity.
- `@Embeddable`: Được sử dụng cho class mà sẽ được nhúng.

```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
}

@Entity
@Table(name = "employees")
public class Employee {
    @Embedded
    private Address address;
}
```

### 6. `@Transient`

- `@Transient`: Đánh dấu một thuộc tính sẽ không được lưu vào cơ sở dữ liệu, chỉ tồn tại trong mã nguồn.

```java
@Transient
private int age;

```

### 7. `@CreationTimestamp` và `@UpdateTimestamp`

- `@CreationTimestamp`: Tự động lưu thời điểm tạo bản ghi.
- `@UpdateTimestamp`: Tự động cập nhật thời điểm sửa đổi bản ghi.

```java
@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(name = "updated_at")
private LocalDateTime updatedAt;
```

## Mở rộng cách sử dụng

### **Ví dụ**: `@ManyToOne`, `@OneToMany`, `@OneToOne`, và `@ManyToMany`:

Dưới đây là ví dụ về cách sử dụng các quan hệ giữa hai entity `Employee` và `Department` để biểu diễn mối quan hệ **`@ManyToOne`**, **`@OneToMany`**, **`@OneToOne`**, và **`@ManyToMany`** trong Spring Boot.

### 1. `@ManyToOne` và `@OneToMany`

Quan hệ `@ManyToOne` và `@OneToMany` thường được sử dụng khi một `Department` có thể chứa nhiều `Employee`, nhưng mỗi `Employee` chỉ thuộc về một `Department`.

#### Ví dụ

**Entity `Employee`**:

```java
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Getters và Setters
}
```

**Entity `Department`**:

```java
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    // Getters và Setters
}
```

#### Giải thích

- **`@ManyToOne`** trên `Employee`: Một `Employee` thuộc về một `Department`, được liên kết bằng `department_id`.
- **`@OneToMany`** trên `Department`: Một `Department` có thể chứa nhiều `Employee`. Thuộc tính `mappedBy` chỉ ra rằng `Department` không trực tiếp quản lý khóa ngoại mà là thuộc tính `department` trong `Employee`.

### 2. `@OneToOne`

Quan hệ `@OneToOne` thường được sử dụng khi một `Employee` chỉ có một `Profile` và ngược lại. Mỗi `Employee` có một `Profile` riêng biệt, và không thể chia sẻ với nhân viên khác.

#### Ví dụ

**Entity `Employee`**:

```java
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    // Getters và Setters
}
```

**Entity `Profile`**:

```java
import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bio")
    private String bio;

    @OneToOne(mappedBy = "profile")
    private Employee employee;

    // Getters và Setters
}
```

#### Giải thích

- **`@OneToOne`** trên `Employee`: Mỗi `Employee` có một `Profile` được ánh xạ qua `profile_id`.
- **`@OneToOne`** trên `Profile`: Đảm bảo `Profile` chỉ có thể liên kết với một `Employee`, thuộc tính `mappedBy` chỉ ra rằng `Employee` là chủ quản lý khóa ngoại.

### 3. `@ManyToMany`

Quan hệ `@ManyToMany` thường được sử dụng khi một `Employee` có thể thuộc nhiều `Project`, và một `Project` có thể bao gồm nhiều `Employee`. Để biểu diễn mối quan hệ này, cần một bảng trung gian `employee_project`.

#### Ví dụ

**Entity `Employee`**:

```java
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToMany
    @JoinTable(
        name = "employee_project",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

    // Getters và Setters
}
```

**Entity `Project`**:

```java
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    // Getters và Setters
}
```

#### Giải thích

- **`@ManyToMany`** trên `Employee` và `Project`: `Employee` và `Project` có quan hệ nhiều-nhiều, nghĩa là một `Employee` có thể tham gia nhiều `Project` và ngược lại.
- **`@JoinTable`**: Xác định bảng trung gian `employee_project` với hai khóa ngoại `employee_id` và `project_id`.
- **`mappedBy`** trong `Project`: Đảm bảo `Project` biết rằng `Employee` là nơi quản lý mối quan hệ.
