package com.example.BookStore.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
@Table(name="students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message = "Mã sinh viên không được để trống")
	@Size(max=50, message = "Độ dài mã sinh viên không vượt quá 50 ký tự")
	@Column(name = "msv", length = 50, unique= true)
	private String msv;
	
	@NotBlank(message = "Sinh viên không được để trống")
	@Size(max=50, message = "Độ dài tên không vượt quá 50 ký tự")
	@Pattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯăêôơÁẮẤÉẾÍÓỐÔÚỨỳỵỷỹÝỲ\\s]+$",
	message = "Tên sinh viên không được chứa số hoặc ký tự đặc biệt")
	@Column(name = "name", length = 50)
	private String name;
	
	@NotNull(message = "Ngày sinh không được để trống")
	@Past(message = "Ngày sinh phải nhỏ hơn ngày hiện tại")
	@Column(name = "birthday", length = 50)
	private LocalDate birthday;
	
	@NotNull(message = "Tên lớp không được để trống")
	@Size(max=50, message = "Độ dài tên lớp không vượt quá 50 ký tự")
	@Column(name = "className", length= 50)
	private String className;
	
	@NotNull(message = "Khoa không được để trống")
	@Size(max=50, message = "Độ dài tên khoa không vượt quá 50 ký tự")
	@Column(name = "khoa", length= 50)
	private String khoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsv() {
		return msv;
	}

	public void setMsv(String msv) {
		this.msv = msv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getKhoa() {
		return khoa;
	}

	public void setKhoa(String khoa) {
		this.khoa = khoa;
	}
	
	
}
