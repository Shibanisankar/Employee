package com.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.dao.EmployeeDaoJpa;
import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.exception.InvalidDataException;
import com.example.util.EmployeeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.bretty.console.tree.TreePrinter;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDaoJpa employeeDaoJpa;
	
	int level1 =-1;
	int level2 =-1;
	boolean flag = false;

	@Override
	public String store(InputStream inputStream) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		try {
			EmployeeDTO[] employeeDTOs = mapper.readValue(inputStream, EmployeeDTO[].class);
			Set<Employee> employeeSet = EmployeeConverter.convert(employeeDTOs);
			if (employeeSet.size() != employeeDTOs.length) {
				throw new InvalidDataException("Duplicate record found. Please reverify the json file");
			}
			employeeDaoJpa.saveAll(employeeSet);
			return "OK";

		} catch (IOException e) {
			throw new InvalidDataException("Invalid Json file");
		} catch (EntityNotFoundException e) {
			throw new InvalidDataException("FK constraint failed. Please reverify the json file");
		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String updateManager(Long managerId, Long employeeId) {
		try {
			Employee employee = employeeDaoJpa.getOne(employeeId);
			employee.setManager_fk(managerId);
			return "OK";
		} catch (EntityNotFoundException e) {
			throw new InvalidDataException("Invalid Employee/Manager ID");
		}
	}

	@Override
	public Long getMaxSub() {
		return employeeDaoJpa.getMaxSub();
	}

	@Override
	public Long getTotalSalary(Long id) {
		if (employeeDaoJpa.existsById(id)) {
			Set<Long> allSubIds = new HashSet<>();
			Set<Long> imediateSubIds = new HashSet<>();
			imediateSubIds.add(id);
			while (!CollectionUtils.isEmpty(imediateSubIds)) {
				imediateSubIds = employeeDaoJpa.getImediateSubIds(imediateSubIds);
				allSubIds.addAll(imediateSubIds);
			}
			Long total = employeeDaoJpa.getSumSalaray(allSubIds);
			return total == null ? 0l : total;
		}else {
			throw new InvalidDataException("Invalid employee Id");
		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String printOrgStructure() {
		Employee root = employeeDaoJpa.findByManager(null);
		String output = TreePrinter.toString(root);
		System.out.println(output);
		return output;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Boolean isSameLevel(Long firstEmpId, Long secondEmpId) {
		Employee root = employeeDaoJpa.findByManager(null);
		Set<Employee> subordinates = new HashSet<>();
		subordinates.add(root);
		checkLevel(firstEmpId,secondEmpId , subordinates);
		return flag;
	}

	private void checkLevel(Long firstEmpId , Long secondEmpId, Set<Employee> subordinates) {
		if(CollectionUtils.isEmpty(subordinates)) {
			return;
		}
		for(Employee employee : subordinates) {
			if(firstEmpId != null &&firstEmpId.equals(employee.getId())) {
				firstEmpId = null;
			}
			if(secondEmpId != null && secondEmpId.equals(employee.getId())) {
				secondEmpId = null;
			}
			if(firstEmpId == null && secondEmpId == null) {
				if(level1 == level2) {
					flag = true;
				}else {
					flag = false;
				}
				return;
			}
		}
		if(firstEmpId != null) {
			level1++;
		}
		if(secondEmpId != null) {
			level2++;
		}
		for(Employee employee : subordinates) {
			checkLevel(firstEmpId, secondEmpId, employee.getSubordinates());
		}
		return;
	}


}
