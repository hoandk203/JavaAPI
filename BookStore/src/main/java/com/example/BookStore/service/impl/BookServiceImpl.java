package com.example.BookStore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookStore.entity.Book;
import com.example.BookStore.repository.BookRepository;
import com.example.BookStore.service.BookService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book postBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public void deleteBook(Long bookId) {
		bookRepository.deleteById(bookId);
	}

	@Override
	public Book getBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		return book.orElse(null);
	}

	@Override
	public Book putBook(Book book) {
		if(bookRepository.existsById(book.getId())) {
			return bookRepository.save(book);
		}
		return null;
	}

	@Override
	public List<Book> searchBooks(String keyword) {
		return bookRepository.searchByKeyword(keyword);
	}

}
