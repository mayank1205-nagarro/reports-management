package com.mayank.reports_management.service.impl;

import com.mayank.reports_management.entity.Department;
import com.mayank.reports_management.repository.DepartmentRepository;
import com.mayank.reports_management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(String name) {

        Department department = Department.builder()
                .name(name)
                .build();

        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}