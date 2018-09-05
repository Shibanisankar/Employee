package com.example.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Employee;

@Repository
public interface EmployeeDaoJpa extends JpaRepository<Employee, Long>{

	@Query(nativeQuery=true,value="SELECT id FROM (SELECT manager_id as id,COUNT(*) AS counted FROM employee GROUP BY manager_id) order by counted desc limit 1")
	Long getMaxSub();

	@Query(nativeQuery=true,value="SELECT employee_id from employee where manager_id in :ids")
	Set<Long> getImediateSubIds(@Param("ids") Set<Long> ids);

	@Query(nativeQuery=true,value="SELECT SUM(salary) from employee where employee_id in :ids")
	Long getSumSalaray(@Param("ids")Set<Long> ids);

	Employee findByManager(Employee employee);

}
