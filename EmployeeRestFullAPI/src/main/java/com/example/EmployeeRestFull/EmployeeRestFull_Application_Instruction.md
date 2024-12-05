# Hướng Dẫn Sử Dụng Ứng Dụng EmployeeRestFull

## Giới Thiệu

Ứng dụng `EmployeeRestFull` là một ứng dụng RESTful API được xây dựng bằng Spring Boot, cho phép quản lý thông tin nhân viên. Ứng dụng sử dụng các phương thức HTTP để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên dữ liệu nhân viên.

## Cấu Trúc Dự Án

### Lớp Chính: `EmployeeRestFullApplication`

```java
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
