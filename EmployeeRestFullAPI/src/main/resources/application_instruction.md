# Hướng Dẫn Cấu Hình Database

> File cấu hình kết nối MySQL trong Spring Boot Application

## Nội dung cấu hình

```properties
spring.application.name=helloRestFull
spring.datasource.url=jdbc:mysql://localhost:3308/database_name?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## Giải thích từng dòng cấu hình

- `spring.application.name=helloRestFull`:

  - **Mô tả**: Đặt tên cho ứng dụng Spring Boot.
  - **Ý nghĩa**: Tên này có thể được sử dụng để xác định ứng dụng trong log hoặc khi có nhiều ứng dụng chạy trên cùng một server.

- `spring.datasource.url=jdbc:mysql://localhost:3308/database_name?useSSL=false&allowPublicKeyRetrieval=true`:

  - Mô tả: URL kết nối đến database MySQL.
  - Ý nghĩa: URL bao gồm địa chỉ của database server (`localhost`), cổng MySQL (`3308`), tên database (`database_name`), và các tham số tùy chọn:
    - `useSSL=false`: Tắt SSL để tránh cảnh báo kết nối không bảo mật, dùng cho môi trường phát triển.
    - `allowPublicKeyRetrieval=true`: Cho phép client yêu cầu khóa công khai từ server khi sử dụng mật khẩu.

- `spring.datasource.username=root`:

  - `Mô tả`: Tên tài khoản MySQL được dùng để kết nối.
  - `Ý nghĩa`: Sử dụng tài khoản `root` để truy cập, chỉ nên dùng cho môi trường phát triển hoặc kiểm thử, không khuyến khích dùng trong production.

- `spring.datasource.password=123456`:

  - Mô tả: Mật khẩu cho tài khoản MySQL.
  - Ý nghĩa: Là mật khẩu để xác thực cho tài khoản `root`.

- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect:

  - `Mô tả`: Dialect cho Hibernate, được cấu hình để sử dụng MySQL.
  - `Ý nghĩa`: Hibernate sử dụng thông tin dialect để tạo các truy vấn SQL tương thích với MySQL.

- spring.jpa.hibernate.ddl-auto=update:

  - `Mô tả`: Quy tắc tự động cập nhật cấu trúc bảng trong database.
  - `Ý nghĩa`: Giá trị `update` cho phép Hibernate tự động điều chỉnh schema trong database để phù hợp với các thay đổi trong code mà không làm mất dữ liệu cũ.

## Chi tiết cấu hình

| Thuộc tính                                | Mô tả                            | Giá trị mặc định                          |
| ----------------------------------------- | -------------------------------- | ----------------------------------------- |
| `spring.application.name`                 | Tên ứng dụng Spring Boot         | helloRestFull                             |
| `spring.datasource.url`                   | URL kết nối MySQL                | jdbc:mysql://localhost:3308/database_name |
| `spring.datasource.username`              | Username database                | root                                      |
| `spring.datasource.password`              | Password database                | 123456                                    |
| `spring.jpa.properties.hibernate.dialect` | Dialect cho MySQL                | org.hibernate.dialect.MySQLDialect        |
| `spring.jpa.hibernate.ddl-auto`           | Chế độ tự động tạo/cập nhật bảng | update                                    |

## Các bước thực hiện

### 1. Cài đặt MySQL

- Tải và cài đặt MySQL Server
- Đảm bảo MySQL đang chạy trên port 3308
- Tạo database mới:

```sql
CREATE DATABASE database_name;
```

### 2. Cấu hình ứng dụng

1. Tạo file `application.properties` trong thư mục:
   ```
   src/main/resources/application.properties
   ```
2. Copy các cấu hình ở trên vào file
3. Điều chỉnh các thông số phù hợp với môi trường:
   - Port MySQL
   - Tên database
   - Username/password

### 3. Kiểm tra kết nối

- Khởi động ứng dụng Spring Boot
- Kiểm tra log để đảm bảo kết nối thành công
- Xử lý các lỗi nếu có

## Một số lưu ý

### Các giá trị của `ddl-auto`:

- `update`: Tự động cập nhật schema
- `create`: Tạo mới schema mỗi lần chạy
- `create-drop`: Tạo mới và xóa khi dừng
- `validate`: Kiểm tra schema
- `none`: Không thay đổi schema

### Bảo mật

- Không sử dụng tài khoản root trong production
- Đặt password mạnh
- Bật SSL trong môi trường production
- Sử dụng biến môi trường cho thông tin nhạy cảm

## Troubleshooting

### 1. Lỗi kết nối

```
Connection refused to host: localhost port: 3308
```

**Giải pháp:**

- Kiểm tra MySQL đang chạy
- Xác nhận port đúng
- Kiểm tra firewall

### 2. Lỗi xác thực

```
Access denied for user 'root'
```

**Giải pháp:**

- Kiểm tra username/password
- Kiểm tra quyền của user

### 3. Lỗi database

```
Database database_name not found
```

**Giải pháp:**

- Tạo database
- Kiểm tra tên database trong URL

## Tham khảo thêm

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---
