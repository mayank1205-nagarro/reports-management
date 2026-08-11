package com.mayank.reports_management.controller;

import com.mayank.reports_management.dto.request.DepartmentRequest;
import com.mayank.reports_management.entity.Department;
import com.mayank.reports_management.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(
            summary = "Create a department",
            description = "Creates a new department. Only ADMIN users can access this API."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Department created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid department details"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication token is missing or invalid"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only ADMIN users can create departments"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Department already exists"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public Department createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(
                request.getName());
    }

    @GetMapping
    @Operation(
            summary = "Get all departments",
            description = "Returns all predefined departments available for report assignment."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departments fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication token is missing or invalid"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public List<Department> getAllDepartments() {

        return departmentService.getAllDepartments();
    }
}