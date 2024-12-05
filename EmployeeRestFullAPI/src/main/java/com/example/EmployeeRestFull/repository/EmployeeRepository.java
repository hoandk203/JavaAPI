package com.example.EmployeeRestFull.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EmployeeRestFull.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("SELECT e FROM Employee e " +
		       "WHERE (CAST(e.id AS string) LIKE CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		List<Employee> searchByKeyword(@Param("keyword") String keyword);
}
