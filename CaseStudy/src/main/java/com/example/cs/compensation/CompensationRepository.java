package com.example.cs.compensation;



import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
	@Query(value = "SELECT * FROM compensation comp WHERE comp.type = ?1 AND comp.id_employee = ?2", nativeQuery = true)
	public Compensation findExistingSalary(String type, int id_employee);


	@Query(value = "SELECT id, type, description, datec, id_employee, SUM(amount) AS amount,"
			+ "YEAR(datec) AS year, MONTHNAME(datec) AS month "
			+ "FROM compensation WHERE id_employee = :id_employee "
			+ "GROUP BY year, month ORDER BY datec ASC", nativeQuery = true)
	List<Compensation> findCompensationsById(@Param("id_employee") int id_employee);

	@Query(value = "SELECT id, type, description, id_employee, datec, SUM(amount) AS amount,"
			+ "YEAR(datec) AS year, MONTHNAME(datec) AS month "
			+ "FROM compensation WHERE id_employee = :id_employee AND datec BETWEEN :startDate AND :endDate "
			+ "GROUP BY year, month ORDER BY datec ASC", nativeQuery = true)
	public List<Compensation> findCompensationByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("id_employee") int id_employee);

	@Query(value = "SELECT * FROM Compensation WHERE MONTH(datec) = :month AND YEAR(datec) = :year "
			+ "AND id_employee = :id", nativeQuery = true)
	public List<Compensation> findDetailsByMonth(int id, String month, int year);
	
	
	
	@Query(value="SELECT id FROM compensation "
			+ "WHERE datec LIKE CONCAT(YEAR(?1), '-', MONTH(?1), '-%') AND id_employee = ?2 AND type = 'Salary' LIMIT 1", nativeQuery=true)
	public Long checkEmployeeSalary(Date datec, int id_employee);
	
	
}

