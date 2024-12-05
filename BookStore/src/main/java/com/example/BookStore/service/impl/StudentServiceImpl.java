package com.example.BookStore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookStore.entity.Student;
import com.example.BookStore.repository.StudentRepository;
import com.example.BookStore.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Student postStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public void deleteStudent(Long studentId) {
		studentRepository.deleteById(studentId);
	}

	@Override
	public Student getStudentById(Long studentId) {
		Optional<Student> student = studentRepository.findById(studentId);
		return student.orElse(null);
	}

	@Override
	public Student putStudent(Student student) {
		if(studentRepository.existsById(student.getId())) {
			return studentRepository.save(student);
		}
		return null;
	}

	@Override
	public List<Student> searchStudents(String keyword) {
		return studentRepository.searchByKeyword(keyword);
	}

}
