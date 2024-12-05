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

import com.example.BookStore.entity.Book;
import com.example.BookStore.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	// Lay danh sach 
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	// Lay thong tin nhan vien theo ID
	@GetMapping("{id}")
	public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
		Optional<Book> existBook = Optional.ofNullable(bookService.getBookById(id));
		if(existBook.isPresent()) {
			return new ResponseEntity<>(existBook.get(), HttpStatus.OK);
		} else {
			Map<String, String> errorReponse = new HashMap<>();
			errorReponse.put("message", "Book not found with Id: " + id);
			return new ResponseEntity<>(errorReponse, HttpStatus.NOT_FOUND);
		}
	}
	
	// Tao moi
	@PostMapping
	public ResponseEntity<?> postBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
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
            Book bookData = bookService.postBook(book);
            return new ResponseEntity<>(bookData, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Errror");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
	}
	
	// Cap nhat
	@PutMapping("{id}")
	public ResponseEntity<?> putBook(@PathVariable("id") Long id, @Valid @RequestBody Book book, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation error");
            errorResponse.put("details", bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .reduce((error1, error2) -> error1 + ", " + error2)
                    .orElse("Unknown validation error"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} 
		Optional<Book> existingbook = Optional.ofNullable(bookService.getBookById(id));
        if (existingbook.isPresent()) {
            book.setId(id);
            try {
                Book updatedbook = bookService.putBook(book);
                return new ResponseEntity<>(updatedbook, HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Error");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "book not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
	}
	
    // Xóa nhân viên theo ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletebookById(@PathVariable("id") Long id) {
        Optional<Book> book = Optional.ofNullable(bookService.getBookById(id));
        if (book.isPresent()) {
            bookService.deleteBook(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "book successfully deleted");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "book not found with ID: " + id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
	// Tim kiem tren tat ca cac truong
	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam("keyword") String keyword) {
		List<Book> seardchbooks = bookService.searchBooks(keyword);
		return new ResponseEntity<>(seardchbooks, HttpStatus.OK);
	}
}
