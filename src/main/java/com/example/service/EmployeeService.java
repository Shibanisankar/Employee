package com.example.service;

import java.io.InputStream;

public interface EmployeeService {
	public String store(InputStream inputStream) throws Exception;

	public String updateManager(Long managerId, Long employeeId);

	public Long getMaxSub();

	public Long getTotalSalary(Long id);

	public String printOrgStructure();
	
	public Boolean isSameLevel(Long firstEmpId,Long secondEmpId);
}
