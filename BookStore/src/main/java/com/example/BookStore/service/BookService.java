package com.example.BookStore.service;

import java.util.List;

import com.example.BookStore.entity.Book;

public interface BookService {
	// Them moi
	Book postBook(Book book);
	
	// Lay danh sach
	List<Book> getAllBooks();
	
	// Xoa 
	void deleteBook(Long bookId);
	
	// Lay theo ID
	Book getBookById(Long bookId);
	
	// Cap nhat 
	Book putBook(Book book);
	
	// Tim kiem tren tat ca cac truong
	List<Book> searchBooks(String keyword);
}
