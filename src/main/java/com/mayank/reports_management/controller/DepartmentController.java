package com.mayank.reports_management.controller;

import com.mayank.reports_management.dto.request.DepartmentRequest;
import com.mayank.reports_management.entity.Department;
import com.mayank.reports_management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public Department createDepartment(
            @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(
                request.getName());
    }

    @GetMapping
    public List<Department> getAllDepartments() {

        return departmentService.getAllDepartments();
    }
}