package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BookStore.entity.Book;


public interface BookRepository extends JpaRepository<Book, Long>  {
	@Query("SELECT b FROM Book b " +
		       "WHERE CAST(b.id AS string) LIKE CONCAT('%', :keyword, '%') " +
		       "OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR STR(b.publishedDate) LIKE CONCAT('%', :keyword, '%') " +
		       "OR CAST(b.price AS string) LIKE CONCAT('%', :keyword, '%') " +
		       "OR CAST(b.quantity AS string) LIKE CONCAT('%', :keyword, '%')")
		List<Book> searchByKeyword(@Param("keyword") String keyword);
}
