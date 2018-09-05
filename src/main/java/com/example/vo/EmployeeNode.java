package com.example.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Employee;

import io.bretty.console.tree.PrintableTreeNode;

public class EmployeeNode extends Employee implements PrintableTreeNode{

	@Override
	public List<? extends PrintableTreeNode> children() {
		return new ArrayList<>(this.getSubordinates());
	}

	@Override
	public String name() {
		return this.getFirstName();
	}

}
