const API_URL = "http://localhost:8080/api/students"; // Cập nhật với URL cơ sở của API

// Khi tài liệu đã được tải xong, gọi hàm fetchEmployees
document.addEventListener("DOMContentLoaded", () => {
    fetchEmployees();
});

// Hàm để lấy tất cả nhân viên
function fetchEmployees() {
    fetch(API_URL) // Gửi yêu cầu GET đến API để lấy danh sách nhân viên
        .then((response) => response.json()) // Chuyển đổi phản hồi sang định dạng JSON
        .then((employees) => {
            localStorage.setItem("employees", JSON.stringify(employees));
            populateEmployeeTable(employees); // Gọi hàm để điền dữ liệu vào bảng
        })
        .catch((error) => console.error("Error fetching employees:", error)); // Xử lý lỗi
}

// Hàm để tìm kiếm nhân viên theo từ khóa
function searchEmployees() {
    const keyword = document.getElementById("searchInput").value.trim(); // Lấy từ khóa tìm kiếm
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
        .catch((error) => console.error("Error searching employees:", error)); // Xử lý lỗi
}

// Hàm để điền dữ liệu nhân viên vào bảng
function populateEmployeeTable(employees) {
    const tableBody = document.querySelector("#table_id tbody"); // Lấy phần thân của bảng
    tableBody.innerHTML = ""; // Xóa dữ liệu cũ trong bảng

    employees.forEach((employee) => {
        // Lặp qua từng nhân viên
        const row = document.createElement("tr"); // Tạo một hàng mới trong bảng
        row.innerHTML = `
            <td class="px-6 py-4 whitespace-nowrap">${employee.id}</td>
            <td class="px-6 py-4 whitespace-nowrap">${employee.msv}</td>
            <td class="px-6 py-4 whitespace-nowrap">${employee.name}</td>
            <td class="px-6 py-4 whitespace-nowrap">${employee.birthday}</td>
            <td class="px-6 py-4 whitespace-nowrap">${employee.className}</td>
            <td class="px-6 py-4 whitespace-nowrap">${employee.khoa}</td>
            <td class="flex px-6 py-4 whitespace-nowrap space-x-2">
                <button
                    onclick="window.location.href='update.html?id=${employee.id}'"
                    class="flex text-white bg-yellow-500 hover:bg-yellow-600 focus:ring-4 focus:outline-none focus:ring-yellow-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-yellow-400 dark:hover:bg-yellow-500 dark:focus:ring-yellow-800"
                    type="button">
                    Sửa
                </button>
                <button
                    onclick="deleteEmployee(${employee.id})"
                    class="flex text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800">
                    Xóa
                </button>
            </td>
        `;
        tableBody.appendChild(row); // Thêm hàng mới vào thân bảng
    });
}

// Hàm để xóa một nhân viên theo ID
function deleteEmployee(id) {
    if (confirm("Bạn có chắc chắn muốn xóa nhân viên này không?")) {
        // Hiển thị hộp thoại xác nhận
        fetch(`${API_URL}/${id}`, {
            method: "DELETE", // Gửi yêu cầu DELETE đến API
        })
            .then(() => {
                window.location.href = "app.html?success=delete"; // Chuyển hướng về trang chính sau khi xóa thành công
            })
            .catch((error) => console.error("Error deleting employee:", error)); // Xử lý lỗi
    }
}

// THÊM XE ////////////////////////////
document.getElementById("addForm").addEventListener("submit", function (e) {
    e.preventDefault(); // Ngăn chặn hành vi mặc định của việc gửi biểu mẫu

    const newEmployee = {
        msv: document.getElementById("msv").value, // Lấy giá trị từ trường Tên
        name: document.getElementById("name").value, // Lấy giá trị từ trường Họ
        birthday: document.getElementById("birthday").value, // Lấy giá trị từ trường Email
        className: document.getElementById("className").value, // Lấy giá trị từ trường Email
        khoa: document.getElementById("khoa").value, // Lấy giá trị từ trường Email
    };

    // Gửi dữ liệu tới API
    fetch(API_URL, {
        method: "POST", // Phương thức gửi là POST
        headers: { "Content-Type": "application/json" }, // Đặt tiêu đề Content-Type
        body: JSON.stringify(newEmployee), // Chuyển đổi đối tượng newEmployee thành chuỗi JSON
    })
        .then((response) => {
            if (!response.ok) {
                // Kiểm tra xem phản hồi có thành công không
                return response.json().then((errorData) => {
                    // Hiển thị thông báo lỗi nếu có
                    document.getElementById("error-message").textContent =
                        errorData.details + "\n" + errorData.message; // Hiển thị thông điệp lỗi
                    throw new Error(
                        errorData.details + "\n" + errorData.message
                    ); // Ném lỗi để xử lý
                });
            }
            return response.json(); // Chuyển đổi phản hồi thành JSON
        })
        .then(() => {
            window.location.href = "app.html?success=add"; // Chuyển hướng đến trang index với thông báo thành công
        })
        .catch((error) => console.error("Error:", error)); // In lỗi ra console nếu có
});
