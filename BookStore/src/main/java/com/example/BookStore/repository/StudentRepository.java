package com.example.BookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BookStore.entity.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s " +
           "WHERE LOWER(CAST(s.id AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(s.msv) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR STR(s.birthday) LIKE CONCAT('%', :keyword, '%') " +
           "OR LOWER(s.className) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(s.khoa) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);
}
