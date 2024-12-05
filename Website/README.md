# Hướng dẫn sử dụng Website CRUD

## Giới thiệu

Hệ thống CRUD (Create, Read, Update, Delete) này cho phép bạn quản lý danh sách, bao gồm thêm, xem, sửa và xóa thông tin.

## Cấu trúc dự án

Dự án này bao gồm hai tệp HTML chính:

- `index.html`: Trang chính để hiển thị danh sách.
- `add.html`: Trang để thêm mới.
- `edit.html`: Trang để cập nhật thông tin.

### Các tệp JavaScript

- `app.js`: Chứa logic xử lý cho việc lấy, thêm, tìm kiếm và xóa.

## Hướng dẫn sử dụng

### 1. Trang chính (index.html)

#### a. Giao diện

- **Danh sách**: Hiển thị danh sách tất cả trong bảng.
- **Thanh tìm kiếm**: Tìm kiếm theo tên.
- **Nút Thêm**: Chuyển đến trang thêm mới.
- **Nút Cập nhật**: Chuyển đến trang cập nhât mới.

#### b. Chức năng

- **Lấy danh sách**: Khi tải trang, danh sách sẽ được tự động lấy từ API và hiển thị trong bảng.
- **Tìm kiếm**: Nhập từ khóa vào ô tìm kiếm và nhấn nút "Tìm kiếm" để lọc danh sách.
- **Xóa**: Nhấn nút "Xóa" bên cạnh tên để xóa đó. Một hộp thoại xác nhận sẽ hiện lên để xác nhận hành động này.

#### c. Thông báo

Thông báo thành công sẽ hiển thị ở trên cùng của danh sách khi bạn thêm, sửa hoặc xóa. Thông báo sẽ biến mất sau một thời gian hoặc khi tải lại trang.

### 2. Trang thêm (add.html)

#### a. Giao diện

- **Biểu mẫu thêm**: Nhập thông tin bao gồm Tên, Họ, và Email.

#### b. Chức năng

- **Thêm**: Khi bạn điền thông tin và nhấn nút "Thêm", dữ liệu sẽ được gửi đến API để thêm vào danh sách.
- **Kiểm tra lỗi**: Nếu có lỗi xảy ra trong quá trình thêm, thông báo lỗi sẽ hiển thị dưới biểu mẫu.

### 3. Trang Cập nhật (edit.html)

#### a. Giao diện

- **Biểu mẫu Cập nhật**: Nhập thông tin bao gồm Tên, Họ, và Email.

#### b. Chức năng

- **Cập nhật**: Khi bạn điền thông tin và nhấn nút "cập nhật", dữ liệu sẽ được gửi đến API để cập nhật vào danh sách.
- **Kiểm tra lỗi**: Nếu có lỗi xảy ra trong quá trình cập nhật, thông báo lỗi sẽ hiển thị dưới biểu mẫu.

### 4. Các yêu cầu API

- **Lấy danh sách**: `GET http://localhost:8080/api/employees`
- **Tìm kiếm**: `GET http://localhost:8080/api/employees/search?keyword={từ_khóa}`
- **Thêm**: `POST http://localhost:8080/api/employees` với dữ liệu JSON chứa thông tin.
- **Xóa**: `DELETE http://localhost:8080/api/employees/{id}`

### 5. Lỗi thông thường

- **Lỗi kết nối**: Nếu bạn không thể kết nối đến API, hãy kiểm tra địa chỉ URL và đảm bảo rằng máy chủ đang chạy.
- **Thông báo lỗi từ API**: Nếu có vấn đề với dữ liệu bạn gửi (ví dụ: email không hợp lệ), thông báo lỗi sẽ hiển thị dưới biểu mẫu.

## Kết luận

Hệ thống CRUD cho này là một cách đơn giản và hiệu quả để quản lý thông tin. Bạn có thể mở rộng và cải thiện hệ thống theo nhu cầu của mình.
