package com.mayank.reports_management.service;

import com.mayank.reports_management.entity.Department;

import java.util.List;

public interface DepartmentService {

    Department createDepartment(String name);

    List<Department> getAllDepartments();
}