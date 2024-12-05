const API_URL = 'http://localhost:8080/api/employees'; // Cập nhật với URL cơ sở của API

// Khi tài liệu đã được tải xong, gọi hàm fetchEmployees
document.addEventListener('DOMContentLoaded', () => {
  fetchEmployees();
});

// Hàm để lấy tất cả nhân viên
function fetchEmployees() {
  fetch(API_URL) // Gửi yêu cầu GET đến API để lấy danh sách nhân viên
    .then((response) => response.json()) // Chuyển đổi phản hồi sang định dạng JSON
    .then((employees) => {
      populateEmployeeTable(employees); // Gọi hàm để điền dữ liệu vào bảng
    })
    .catch((error) => console.error('Error fetching employees:', error)); // Xử lý lỗi
}

// Hàm để tìm kiếm nhân viên theo từ khóa
function searchEmployees() {
  const keyword = document.getElementById('searchInput').value.trim(); // Lấy từ khóa tìm kiếm
  if (!keyword) {
    fetchEmployees(); // Nếu ô tìm kiếm trống, lấy tất cả nhân viên
    return;
  }

  // Gửi yêu cầu tìm kiếm nhân viên theo từ khóa
  fetch(`${API_URL}/search?keyword=${encodeURIComponent(keyword)}`)
    .then((response) => response.json()) // Chuyển đổi phản hồi sang định dạng JSON
    .then((employees) => {
      populateEmployeeTable(employees); // Gọi hàm để điền dữ liệu vào bảng
    })
    .catch((error) => console.error('Error searching employees:', error)); // Xử lý lỗi
}

// Hàm để điền dữ liệu nhân viên vào bảng
function populateEmployeeTable(employees) {
  const tableBody = document.querySelector('#table_id tbody'); // Lấy phần thân của bảng
  tableBody.innerHTML = ''; // Xóa dữ liệu cũ trong bảng

  employees.forEach((employee) => {
    // Lặp qua từng nhân viên
    const row = document.createElement('tr'); // Tạo một hàng mới trong bảng
    row.innerHTML = `
          <td style="border: 1px solid black; padding: 8px">${employee.id}</td>
          <td style="border: 1px solid black; padding: 8px">${employee.firstName}</td>
          <td style="border: 1px solid black; padding: 8px">${employee.lastName}</td>
          <td style="border: 1px solid black; padding: 8px">${employee.email}</td>
          <td style="border: 1px solid black; padding: 8px; text-align: center;">
            <a href="edit.html?id=${employee.id}" 
              style="text-decoration: none; background-color: orange; color: white; padding: 10px 20px; border-radius: 8px; margin-right: 10px;">
              Sửa
            </a>
            <button 
              style="background-color: red; border: none; color: white; padding: 10px 20px; border-radius: 8px;" 
              onclick="deleteEmployee(${employee.id})">
              Xóa
            </button>
          </td>
        `;
    tableBody.appendChild(row); // Thêm hàng mới vào thân bảng
  });
}

// Hàm để xóa một nhân viên theo ID
function deleteEmployee(id) {
  if (confirm('Bạn có chắc chắn muốn xóa nhân viên này không?')) {
    // Hiển thị hộp thoại xác nhận
    fetch(`${API_URL}/${id}`, {
      method: 'DELETE', // Gửi yêu cầu DELETE đến API
    })
      .then(() => {
        window.location.href = 'index.html?success=delete'; // Chuyển hướng về trang chính sau khi xóa thành công
      })
      .catch((error) => console.error('Error deleting employee:', error)); // Xử lý lỗi
  }
}
