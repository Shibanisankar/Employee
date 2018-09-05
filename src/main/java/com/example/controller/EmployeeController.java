package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.EmployeeDTO;
import com.example.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PutMapping("/bulk/upload")
	public ResponseEntity<?> bulkUpload(@RequestParam("jsonFile") MultipartFile uploadedFile) throws Exception {
		return ResponseEntity.ok(employeeService.store(uploadedFile.getInputStream()));
	}
	
	//TODO A JWT token can secure the api 
	@PostMapping("/update/manager")
	public ResponseEntity<?> update(@RequestBody EmployeeDTO employee) throws Exception {
		return ResponseEntity.ok(employeeService.updateManager(employee.getManagerId(),employee.getId()));
	}
	
	@GetMapping("/max/sub")
	public ResponseEntity<?> getMaxSub() throws Exception {
		return ResponseEntity.ok(employeeService.getMaxSub());
	}
	
	@GetMapping("{id}/total/salary/sub")
	public ResponseEntity<?> getTotalSalary(@PathVariable("id") Long employeeId) throws Exception {
		return ResponseEntity.ok(employeeService.getTotalSalary(employeeId));
	}
	@GetMapping("/org/str")
	public ResponseEntity<?> getTotalSalary() throws Exception {
		return ResponseEntity.ok(employeeService.printOrgStructure());
	}
}
