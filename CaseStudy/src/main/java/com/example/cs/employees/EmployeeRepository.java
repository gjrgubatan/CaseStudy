package com.example.cs.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Query("SELECT e FROM Employee e WHERE " +
			 "e.firstName LIKE CONCAT('%',:firstName,'%')" +
			 "AND e.lastName LIKE CONCAT('%',:lastName,'%')"+
			 "AND e.position LIKE CONCAT('%',:position,'%')")
				public List<Employee> searchEmployee(String firstName, String lastName, String position);
	

//	//Search Query
//	@Query(value = "SELECT * FROM employees  WHERE first_name LIKE %?1% OR last_name LIKE %?1% OR position LIKE %?1%", nativeQuery=true)
//	List<Employee> findByKeyword(@Param("keyword") String keyword);
	
	//Duplicate Query
	@Query(value ="SELECT * FROM employees e WHERE e.first_name LIKE CONCAT ('', :first_name, '')"
			+ "AND e.middle_name LIKE CONCAT('', :middle_name, '')"
			+ "AND e.last_name LIKE CONCAT('', :last_name, '')"
			+ "AND e.birth_date LIKE CONCAT('', :birth_date, '')", nativeQuery=true)
	Integer searchDuplicate(String first_name, String last_name, String middle_name, Date birth_date);
	
	Employee findByFirstName(String firstName);
	Employee findByMiddleName(String middleName);
	Employee findByLastName(String lastName);
	Employee findByBirthDate(Date birthDate);

}
