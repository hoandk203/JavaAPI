package com.example.BookStore.service;

import java.util.List;

import com.example.BookStore.entity.Student;

public interface StudentService {
	// Them moi
	Student postStudent(Student student);
	
	// Lay danh sach
	List<Student> getAllStudents();
	
	// Xoa 
	void deleteStudent(Long studentId);
	
	// Lay theo ID
	Student getStudentById(Long studentId);
	
	// Cap nhat 
	Student putStudent(Student student);
	
	// Tim kiem tren tat ca cac truong
	List<Student> searchStudents(String keyword);
}
