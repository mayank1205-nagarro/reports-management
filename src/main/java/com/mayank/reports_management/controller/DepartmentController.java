package com.mayank.reports_management.controller;

import com.mayank.reports_management.dto.request.DepartmentRequest;
import com.mayank.reports_management.entity.Department;
import com.mayank.reports_management.service.DepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(
    name = "Department Management",
    description = "APIs for creating and viewing departments"
)
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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