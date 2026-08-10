package com.mayank.reports_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReportRequest {

    @NotNull
    private Long departmentId;

    @NotBlank
    private String description;
}