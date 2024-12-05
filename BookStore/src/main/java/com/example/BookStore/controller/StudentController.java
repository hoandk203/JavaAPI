package com.example.BookStore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookStore.entity.Student;
import com.example.BookStore.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/students")
public class StudentController {
	
	@Autowired
	private StudentService StudentService;
	
	// Lay danh sach 
	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> Students = StudentService.getAllStudents();
		return new ResponseEntity<>(Students, HttpStatus.OK);
	}
	
	// Lay thong tin nhan vien theo ID
	@GetMapping("{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		Optional<Student> existStudent = Optional.ofNullable(StudentService.getStudentById(id));
		if(existStudent.isPresent()) {
			return new ResponseEntity<>(existStudent.get(), HttpStatus.OK);
		} else {
			Map<String, String> errorReponse = new HashMap<>();
			errorReponse.put("message", "Student not found with Id: " + id);
			return new ResponseEntity<>(errorReponse, HttpStatus.NOT_FOUND);
		}
	}
	
	// Tao moi
	@PostMapping
	public ResponseEntity<?> postStudent(@Valid @RequestBody Student Student, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation error");
            errorResponse.put("details", bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2)
                    .orElse("Unknown validation error"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} 
        try {
            Student StudentData = StudentService.postStudent(Student);
            return new ResponseEntity<>(StudentData, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Errror");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
	}
	
	// Cap nhat
	@PutMapping("{id}")
	public ResponseEntity<?> putStudent(@PathVariable("id") Long id, @Valid @RequestBody Student Student, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation error");
            errorResponse.put("details", bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2)
                    .orElse("Unknown validation error"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} 
		Optional<Student> existingStudent = Optional.ofNullable(StudentService.getStudentById(id));
        if (existingStudent.isPresent()) {
            Student.setId(id);
            try {
                Student updatedStudent = StudentService.putStudent(Student);
                return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Error");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Student not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
	}
	
    // Xóa nhân viên theo ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable("id") Long id) {
        Optional<Student> Student = Optional.ofNullable(StudentService.getStudentById(id));
        if (Student.isPresent()) {
            StudentService.deleteStudent(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Student successfully deleted");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Student not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
	// Tim kiem tren tat ca cac truong
	@GetMapping("/search")
	public ResponseEntity<List<Student>> searchStudents(@RequestParam("keyword") String keyword) {
		List<Student> seardchStudents = StudentService.searchStudents(keyword);
		return new ResponseEntity<>(seardchStudents, HttpStatus.OK);
	}
}
