package com.example.util;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;

public class EmployeeConverter {

	private EmployeeConverter() {/*Restricted Instantiation*/}
	
	public static Employee convert(EmployeeDTO source) {
		Employee employee = new Employee();
		employee.setId(source.getId());
		employee.setCity(source.getCity());
		employee.setFirstName(source.getFirstName());
		employee.setSecondName(source.getSecondName());
		employee.setSalary(source.getSalary());
		employee.setManager_fk(source.getManagerId());
		return employee;
	}
	public static Set<Employee> convert(EmployeeDTO[] source) {
		Set<Employee> employeeList = new LinkedHashSet<>();
		Arrays.asList(source).forEach(employee -> {
		employeeList.add(convert(employee));
		});
		return employeeList;
	}

}
