package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.app.DemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class)
public class EmployeeServiceTest {

	@Resource
	private EmployeeService employeeService;
	
	@Before
	public void setup() throws FileNotFoundException, Exception {
		employeeService.store(new FileInputStream("/Users/jena.shibanisankar/Desktop/valid.json"));
	}
	@Test
	public void getMaxSuboridinates() {
		System.out.println(employeeService.getMaxSub());
	}
	
	@Test
	public void getTotalSalary() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(employeeService.getTotalSalary(scanner.nextLong()));
		scanner.close();
	}
	
	@Test
	public void printOrgStructure() {
		employeeService.printOrgStructure();
	}
	
	@Test
	public void checkEmployeeLevel() {
		try {
		assertEquals(false,employeeService.isSameLevel(2657l, 3947l));
		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
}
