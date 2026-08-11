package com.mayank.reports_management.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for creating new department")
public class DepartmentRequest {

    @Schema(description = "Department name")
    private String name;
}